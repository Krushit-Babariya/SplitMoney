package com.krushit.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krushit.entity.Debt;
import com.krushit.model.DebtModel;
import com.krushit.service.IDebtService;

@RestController
@RequestMapping("/api/debts")
public class DebtController {

	private static final Logger logger = LoggerFactory.getLogger(DebtController.class);

	@Autowired
	private IDebtService debtService;

	@PostMapping("/createDebt/{groupId}")
	public ResponseEntity<String> createDebt(@PathVariable Long groupId) {
		try {
			debtService.createDebt(groupId);
			return new ResponseEntity<>("Debts created successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Error creating debts: {}", e.getMessage());
			return new ResponseEntity<>("Failed to create debts", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateDebt/{id}")
	public ResponseEntity<?> updateDebt(@PathVariable Long id, @RequestBody DebtModel debt) {
		try {
			debt.setId(id);
			DebtModel updatedDebt = debtService.updateDebt(debt);
			if (updatedDebt != null) {
				return new ResponseEntity<>(updatedDebt, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Debt not found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.error("Error updating debt: {}", e.getMessage());
			return new ResponseEntity<>("Failed to update debt", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getDebt/{id}")
	public ResponseEntity<Object> getDebtById(@PathVariable Long id) {
		try {
			Optional<DebtModel> debt = debtService.getDebtById(id);
			return new ResponseEntity<>(debt, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error fetching debt: {}", e.getMessage());
			return new ResponseEntity<>("Failed to fetch debt", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteDebt/{id}")
	public ResponseEntity<String> deleteDebt(@PathVariable Long id) {
		try {
			debtService.deleteDebt(id);
			return new ResponseEntity<>("Debt deleted successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			logger.error("Error deleting debt: {}", e.getMessage());
			return new ResponseEntity<>("Failed to delete debt", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllDebts")
	public ResponseEntity<?> getAllDebts() {
		try {
			List<DebtModel> debts = debtService.getAllDebts();
			return new ResponseEntity<>(debts, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error fetching all debts: {}", e.getMessage());
			return new ResponseEntity<>("Failed to fetch debts", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getDebtByGroupAndFromUser")
	public ResponseEntity<?> getDebtByGroupAndFromUser(@RequestParam Long groupId, @RequestParam Long userId) {
		try {
			List<Debt> debts = debtService.getDebtsByGroupAndFromUser(groupId, userId);
			System.out.println("Group Debts From User :: " + debts);
			return new ResponseEntity<>(debts, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error fetching debts by group and user: {}", e.getMessage());
			return new ResponseEntity<>("Failed to fetch debts", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getDebtByGroupAndToUser")
	public ResponseEntity<?> getDebtByGroupAndToUser(@RequestParam Long groupId, @RequestParam Long userId) {
		try {
			List<Debt> debts = debtService.getDebtsByGroupAndToUser(groupId, userId);
			System.out.println("Group Debts TO User:: " + debts);
			return new ResponseEntity<>(debts, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error fetching debts by group and user: {}", e.getMessage());
			return new ResponseEntity<>("Failed to fetch debts", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
