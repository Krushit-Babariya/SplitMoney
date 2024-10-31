package com.krushit.service;

import com.krushit.entity.Expense;
import com.krushit.model.ExpenseModel;

import java.util.List;
import java.util.Optional;

public interface IExpenseService {

    ExpenseModel createExpense(ExpenseModel expenseModel);

    ExpenseModel updateExpense(Long id, ExpenseModel expenseModel);

    Optional<ExpenseModel> getExpenseById(Long id);

    void deleteExpense(Long id);

    List<ExpenseModel> getAllExpenses();
    
    List<ExpenseModel> getAllExpensesByUserId(Long userId);
    
    List<ExpenseModel> getAllExpensesByUserIdAndGroupId(Long userId, Long groupId);
    
    List<ExpenseModel> getAllExpensesByGroupId(Long groupId);
    
    List<Expense> getExpensesByUserInGroup(Long groupId, Long userId);

}
