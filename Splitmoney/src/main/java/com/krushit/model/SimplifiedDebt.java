package com.krushit.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor
public class SimplifiedDebt {
	private Long fromUser;
	private Long toUser;
	private Double amount;
}
