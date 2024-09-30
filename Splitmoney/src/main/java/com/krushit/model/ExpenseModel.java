package com.krushit.model;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
public class ExpenseModel {
	private Long id;
	private Double cost;
	private String description;
	private String details;
	private LocalDateTime date;
	private String repeatInterval;
	private String currencyCode;
	private Long categoryId;
	private Long groupId;
	private boolean splitEqually;
	private List<RepaymentModel> repayments;
	private List<ExpenseUserModel> users;
}
