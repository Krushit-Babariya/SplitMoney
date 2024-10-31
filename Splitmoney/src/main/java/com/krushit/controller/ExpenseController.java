package com.krushit.controller;

import com.krushit.model.ExpenseModel;
import com.krushit.service.IExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

	@Autowired
	private IExpenseService expenseService;

	@PostMapping("/createExpense")
	public ResponseEntity<?> createExpense(@RequestBody ExpenseModel expense) {
		try {
			ExpenseModel createdExpense = expenseService.createExpense(expense);
			return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error creating expense", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateExpense/{id}")
	public ResponseEntity<?> updateExpense(@PathVariable Long id, @RequestBody ExpenseModel expense) {
		try {
			ExpenseModel updatedExpense = expenseService.updateExpense(id, expense);
			return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error updating expense", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getExpense/{id}")
	public ResponseEntity<Object> getExpenseById(@PathVariable Long id) {
		Optional<ExpenseModel> expenseOpt = expenseService.getExpenseById(id);

		if (expenseOpt.isPresent()) {
			return ResponseEntity.ok(expenseOpt.get()); // Return the found expense
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found"); // Return an error message
		}
	}

	@DeleteMapping("/deleteExpense/{id}")
	public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
		try {
			expenseService.deleteExpense(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error deleting expense", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllExpenses")
	public ResponseEntity<List<ExpenseModel>> getAllExpenses() {
		return ResponseEntity.ok(expenseService.getAllExpenses());
	}

	@GetMapping("/user/{userId}")
	public List<ExpenseModel> getAllExpensesByUserId(@PathVariable Long userId) {
		return expenseService.getAllExpensesByUserId(userId);
	}

	@GetMapping("/user/{userId}/group/{groupId}")
	public List<ExpenseModel> getAllExpensesByUserIdAndGroupId(@PathVariable Long userId, @PathVariable Long groupId) {
		return expenseService.getAllExpensesByUserIdAndGroupId(userId, groupId);
	}

	@GetMapping("/getExpenseBygroup/{groupId}")
	public List<ExpenseModel> getAllExpensesByGroupId(@PathVariable Long groupId) {
		return expenseService.getAllExpensesByGroupId(groupId);
	}

}
