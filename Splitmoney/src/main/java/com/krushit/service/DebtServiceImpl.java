package com.krushit.service;

import com.krushit.entity.Debt;
import com.krushit.repository.DebtRepository;
import com.krushit.service.IDebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DebtServiceImpl implements IDebtService {

    @Autowired
    private DebtRepository debtRepository;

    @Override
    public Debt createDebt(Debt debt) {
        return debtRepository.save(debt);
    }

    @Override
    public Debt updateDebt(Debt debt) {
        return debtRepository.save(debt);
    }

    @Override
    public Optional<Debt> getDebtById(Long id) {
        return debtRepository.findById(id);
    }

    @Override
    public void deleteDebt(Long id) {
        debtRepository.deleteById(id);
    }

    @Override
    public List<Debt> getAllDebts() {
        return debtRepository.findAll();
    }
}
