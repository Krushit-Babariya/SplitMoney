package com.krushit.model;

import lombok.Data;

@Data
public class RepaymentModel {
    private Long id;  
    private UserModel fromUser;
    private UserModel toUser;
    private Double amount;
    private ExpenseModel expense; 
}
