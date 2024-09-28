package com.krushit.service;

import com.krushit.entity.Expense;

import java.util.List;
import java.util.Optional;

public interface IExpenseService {

    Expense createExpense(Expense expense);

    Expense updateExpense(Expense expense);

    Optional<Expense> getExpenseById(Long id);

    void deleteExpense(Long id);

    List<Expense> getAllExpenses();
}
