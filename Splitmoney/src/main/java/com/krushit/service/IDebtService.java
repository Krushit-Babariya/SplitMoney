package com.krushit.service;

import com.krushit.entity.Debt;

import java.util.List;
import java.util.Optional;

public interface IDebtService {

    Debt createDebt(Debt debt);

    Debt updateDebt(Debt debt);

    Optional<Debt> getDebtById(Long id);

    void deleteDebt(Long id);

    List<Debt> getAllDebts();
}
