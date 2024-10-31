package com.krushit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krushit.controller.DebtController;
import com.krushit.entity.Debt;
import com.krushit.entity.ExpenseUser;
import com.krushit.entity.Group;
import com.krushit.model.DebtModel;
import com.krushit.model.SimplifiedDebt;
import com.krushit.repository.DebtRepository;
import com.krushit.repository.ExpenseUserRepository;
import com.krushit.repository.GroupRepository;

@Service
public class DebtServiceImpl implements IDebtService {

    @Autowired
    private DebtRepository debtRepository;

    @Autowired
    private ExpenseUserRepository expenseUserRepository;

    @Autowired
    private GroupRepository groupRepository;

    private static final Logger logger = LoggerFactory.getLogger(DebtController.class);
    @Override
    public void createDebt(Long groupId) {
        logger.info("In Create Debt");
        List<ExpenseUser> expenseUsers = expenseUserRepository.findAllByGroupId(groupId);

        List<int[]> expenseTransactions = getExpenseTransactions(expenseUsers);

        List<SimplifiedDebt> simplifiedDebts = calculateSimplifiedDebts(expenseTransactions);

        Group group = groupRepository.findById(groupId)
                                     .orElseThrow(() -> new RuntimeException("Group not found"));

        for (SimplifiedDebt entry : simplifiedDebts) {
            List<Debt> existingDebts = debtRepository.findByFromUserAndToUserAndGroupIDAndActiveTrue(
                entry.getFromUser(), entry.getToUser(), groupId
            );

            if (!existingDebts.isEmpty()) {
                debtRepository.deleteAll(existingDebts);
            }

            Debt debt = new Debt();
            debt.setFromUser(entry.getFromUser());
            debt.setToUser(entry.getToUser());
            debt.setAmount(entry.getAmount());
            debt.setGroupID(groupId);
            debt.setActive(true);

            debtRepository.save(debt);
        }
    }


    @Override
    public DebtModel updateDebt(DebtModel debtModel) {
        // Update existing debt records
        Optional<Debt> optionalDebt = debtRepository.findById(debtModel.getId());
        if (optionalDebt.isPresent()) {
            Debt debt = optionalDebt.get();
            BeanUtils.copyProperties(debtModel, debt, "id", "group", "active"); 
            debtRepository.save(debt);
            return debtModel;
        } else {
            return null;
        }
    }

    @Override
    public Optional<DebtModel> getDebtById(Long id) {
        Optional<Debt> optionalDebt = debtRepository.findById(id);

        if (optionalDebt.isPresent()) {
            DebtModel debtModel = new DebtModel();
            BeanUtils.copyProperties(optionalDebt.get(), debtModel);
            return Optional.of(debtModel);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteDebt(Long id) {
        debtRepository.deleteById(id);
    }

    @Override
    public List<DebtModel> getAllDebts() {
        return debtRepository.findAll().stream().map(debt -> {
            DebtModel debtModel = new DebtModel();
            BeanUtils.copyProperties(debt, debtModel);
            return debtModel;
        }).collect(Collectors.toList());
    }

    private List<int[]> getExpenseTransactions(List<ExpenseUser> expenseUsers) {
        List<int[]> expenseTransactions = new ArrayList<>();
        for (ExpenseUser user : expenseUsers) {
            expenseTransactions.add(new int[] {
                user.getFromUserId().intValue(),
                user.getToUserId().intValue(),
                user.getOwedShare().intValue()
            });
        }
        return expenseTransactions;
    }

    private List<SimplifiedDebt> calculateSimplifiedDebts(List<int[]> expenses) {
        Map<Long, Double> balanceMap = new HashMap<>();

        for (int[] expense : expenses) {
            Long from = (long) expense[0];
            Long to = (long) expense[1];
            double amount = (double) expense[2];

            balanceMap.put(from, balanceMap.getOrDefault(from, 0.0) - amount);
            balanceMap.put(to, balanceMap.getOrDefault(to, 0.0) + amount);
        }

        List<SimplifiedDebt> simplifiedDebts = new ArrayList<>();
        for (Map.Entry<Long, Double> entry : balanceMap.entrySet()) {
            Long userId = entry.getKey();
            Double balance = entry.getValue();
            if (balance < 0) { // User owes money
                Long toUser = findToUser(userId, balanceMap);
                if (toUser != null) {
                    simplifiedDebts.add(new SimplifiedDebt(userId, toUser, -balance)); 
                }
            }
        }

        return simplifiedDebts;
    }

    private Long findToUser(Long fromUser, Map<Long, Double> balanceMap) {
        for (Map.Entry<Long, Double> entry : balanceMap.entrySet()) {
            if (entry.getValue() > 0) { 
                return entry.getKey();
            }
        }
        return null; 
    }
    
    @Override
    public List<Debt> getDebtsByGroupId(Long groupId) {
        // Fetch debts associated with the specified group
        return debtRepository.findByGroupID(groupId);
    }
}
