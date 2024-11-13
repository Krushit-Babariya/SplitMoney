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

import jakarta.transaction.Transactional;

@Service
public class DebtServiceImpl implements IDebtService {

    @Autowired
    private DebtRepository debtRepository;

    @Autowired
    private ExpenseUserRepository expenseUserRepository;

    @Autowired
    private GroupRepository groupRepository;

    private static final Logger logger = LoggerFactory.getLogger(DebtController.class);

    @Transactional
    @Override
    public void createDebt(Long groupId) {
        logger.info("In Create Debt");
        debtRepository.deleteAllByGroupID(groupId);

        List<ExpenseUser> expenseUsers = expenseUserRepository.findAllByGroupId(groupId);

        List<int[]> expenseTransactions = getExpenseTransactions(expenseUsers);
        List<SimplifiedDebt> individualDebts = calculateNetDebts(expenseTransactions);

        Group group = groupRepository.findById(groupId)
                                     .orElseThrow(() -> new RuntimeException("Group not found"));

        for (SimplifiedDebt entry : individualDebts) {
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

    private List<SimplifiedDebt> calculateNetDebts(List<int[]> expenses) {
        Map<String, Double> debtMap = new HashMap<>();

        for (int[] expense : expenses) {
            Long fromUser = (long) expense[0];
            Long toUser = (long) expense[1];
            double amount = (double) expense[2];

            if (fromUser.equals(toUser)) {
                continue;
            }

            String key = fromUser < toUser ? fromUser + ":" + toUser : toUser + ":" + fromUser;
            double value = fromUser < toUser ? amount : -amount;

            debtMap.put(key, debtMap.getOrDefault(key, 0.0) + value);
        }

        List<SimplifiedDebt> debts = new ArrayList<>();
        for (Map.Entry<String, Double> entry : debtMap.entrySet()) {
            String[] users = entry.getKey().split(":");
            Long user1 = Long.parseLong(users[0]);
            Long user2 = Long.parseLong(users[1]);
            double balance = entry.getValue();

            if (balance > 0) {
                debts.add(new SimplifiedDebt(user1, user2, balance));
            } else if (balance < 0) {
                debts.add(new SimplifiedDebt(user2, user1, -balance));
            }
        }

        return debts;
    }

    @Override
    public List<Debt> getDebtsByGroupId(Long groupId) {
        return debtRepository.findByGroupID(groupId);
    }

    @Override
    public List<Debt> getDebtsByGroupAndFromUser(Long groupId, Long userId) {
        return debtRepository.findByGroupIDAndFromUser(groupId, userId);
    }

    @Override
    public List<Debt> getDebtsByGroupAndToUser(Long groupId, Long userId) {
        return debtRepository.findByGroupIDAndToUser(groupId, userId);
    }
}
