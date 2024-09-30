package com.krushit.model;

import lombok.Data;

@Data
public class ExpenseUserModel {
    private UserModel user;
    private Double paidShare;
    private Double owedShare;
    private Double netBalance;
}