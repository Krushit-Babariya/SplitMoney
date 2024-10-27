package com.krushit.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krushit.entity.Expense;
import com.krushit.entity.ExpenseUser;
import com.krushit.entity.User;
import com.krushit.model.ExpenseModel;
import com.krushit.repository.ExpenseRepository;
import com.krushit.repository.ExpenseUserRepository;

import jakarta.transaction.Transactional;

@Service
public class ExpenseServiceImpl implements IExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private ExpenseUserRepository expenseUserRepository;

	@Override
	public ExpenseModel createExpense(ExpenseModel expenseModel) {
		Expense expense = new Expense();
		BeanUtils.copyProperties(expenseModel, expense);

		if (expenseModel.getCreatedBy() != null) {
			User user = new User();
			user.setId(expenseModel.getCreatedBy().longValue());
			expense.setCreatedBy(user);
		} else {
			throw new RuntimeException("Created by user ID must not be null.");
		}

		List<Long> userIds = new ArrayList<>(expenseModel.getUsers());
		userIds.add(expenseModel.getCreatedBy().longValue());

		if (!userIds.isEmpty()) {
			List<ExpenseUser> expenseUsers = new ArrayList<>();
			Double totalCost = expenseModel.getCost();
			int totalUsers = userIds.size();
			Double owedShare = totalCost / totalUsers;

			for (Long userId : userIds) {
				ExpenseUser expenseUser = new ExpenseUser();
				expenseUser.setFromUserId(expenseModel.getCreatedBy().longValue());
				expenseUser.setToUserId(userId.longValue());
				expenseUser.setExpense(expense);
				expenseUser.setOwedShare(owedShare);
				expenseUser.setNetBalance(0.0);

				expenseUsers.add(expenseUser);
			}

			expense.setUsers(expenseUsers);
		}

		Expense savedExpense = expenseRepository.save(expense);

		ExpenseModel savedExpenseModel = new ExpenseModel();
		BeanUtils.copyProperties(savedExpense, savedExpenseModel);

		List<Long> savedUserIds = savedExpense.getUsers().stream()
				.map(expenseUser -> expenseUser.getToUserId().longValue()) 
				.collect(Collectors.toList());
		savedExpenseModel.setUsers(savedUserIds);

		savedExpenseModel.setCreatedBy(savedExpense.getCreatedBy().getId().longValue());

		return savedExpenseModel;
	}

	@Override
	@Transactional
	public ExpenseModel updateExpense(Long expenseId, ExpenseModel expenseModel) {
		Optional<Expense> expenseOpt = expenseRepository.findById(expenseId);

		if (expenseOpt.isPresent()) {
			Expense expense = expenseOpt.get();
			BeanUtils.copyProperties(expenseModel, expense, "id", "createdBy", "users");

			expense.setCost(expenseModel.getCost());

			List<ExpenseUser> existingExpenseUsers = expense.getUsers();

			Set<Long> newUserIds = new HashSet<>(expenseModel.getUsers());

			Long creatorUserId = expenseModel.getCreatedBy().longValue();
			newUserIds.add(creatorUserId);

			Double totalCost = expenseModel.getCost();
			int totalUsers = newUserIds.size();
			Double owedShare = totalCost / totalUsers;

			for (ExpenseUser expenseUser : existingExpenseUsers) {
				if (newUserIds.contains(expenseUser.getToUserId())) {
					expenseUser.setOwedShare(owedShare);
					expenseUser.setNetBalance(0.0); 
				} else {
					expenseUser.setExpense(null); 
				}
			}

			for (Long userId : newUserIds) {
				boolean userExists = existingExpenseUsers.stream().anyMatch(eu -> eu.getToUserId().equals(userId));

				if (!userExists) {
					ExpenseUser newExpenseUser = new ExpenseUser();
					newExpenseUser.setFromUserId(creatorUserId); 
					newExpenseUser.setToUserId(userId);
					newExpenseUser.setExpense(expense);
					newExpenseUser.setOwedShare(owedShare);
					newExpenseUser.setNetBalance(0.0);
					existingExpenseUsers.add(newExpenseUser);
				}
			}

			Expense updatedExpense = expenseRepository.save(expense);
			return mapToModel(updatedExpense);
		} else {
			throw new RuntimeException("Expense not found with expense_id " + expenseId);
		}
	}

	@Override
	public Optional<ExpenseModel> getExpenseById(Long id) {
		Optional<Expense> expense = expenseRepository.findById(id);
		return expense.map(this::mapToModel);
	}

	@Override
	@Transactional
	public void deleteExpense(Long expenseId) {
		Optional<Expense> expenseOpt = expenseRepository.findById(expenseId);

		if (expenseOpt.isPresent()) {
			Expense expense = expenseOpt.get();

			List<ExpenseUser> expenseUsers = expense.getUsers();

			if (expenseUsers != null && !expenseUsers.isEmpty()) {
				expenseUserRepository.deleteAll(expenseUsers); 
			}

			expenseRepository.delete(expense);
		} else {
			throw new RuntimeException("Expense not found with id " + expenseId);
		}
	}

	@Override
	public List<ExpenseModel> getAllExpenses() {
		List<Expense> expenses = expenseRepository.findAll(); 
		return expenses.stream().map(this::mapToModel).collect(Collectors.toList());
	}

	@Override
	public List<ExpenseModel> getAllExpensesByUserId(Long userId) {
		List<Expense> expenses = expenseRepository.findAllByCreatedBy(userId);
		return expenses.stream().map(this::mapToModel).collect(Collectors.toList());
	}

	@Override
	public List<ExpenseModel> getAllExpensesByUserIdAndGroupId(Long userId, Long groupId) {
		List<Expense> expenses = expenseRepository.findAllByCreatedByAndGroupId(userId, groupId);
		return expenses.stream().map(this::mapToModel).collect(Collectors.toList());
	}

	@Override
	public List<ExpenseModel> getAllExpensesByGroupId(Long groupId) {
		List<Expense> expenses = expenseRepository.findAllByGroupId(groupId);
		return expenses.stream().map(this::mapToModel).collect(Collectors.toList());
	}

	private Expense mapToEntity(ExpenseModel model) {
		Expense expense = new Expense();

		BeanUtils.copyProperties(model, expense);

		if (model.getUsers() != null && !model.getUsers().isEmpty()) {
			List<ExpenseUser> expenseUsers = new ArrayList<>();
			for (Long userId : model.getUsers()) {
				ExpenseUser expenseUser = new ExpenseUser();
				expenseUser.setToUserId(userId.longValue());
				expenseUser.setExpense(expense);
				expenseUsers.add(expenseUser);
			}
			expense.setUsers(expenseUsers);
		}

		return expense;
	}

	private ExpenseModel mapToModel(Expense entity) {
		ExpenseModel model = new ExpenseModel();
		BeanUtils.copyProperties(entity, model);

		List<Long> userIds = entity.getUsers().stream().map(expenseUser -> expenseUser.getToUserId().longValue())
				.collect(Collectors.toList());
		model.setUsers(userIds);

		if (entity.getCreatedBy() != null) {
			model.setCreatedBy(entity.getCreatedBy().getId().longValue());
		}

		return model;
	}

}
