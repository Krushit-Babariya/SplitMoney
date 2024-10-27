package com.krushit.model;

import lombok.Data;

@Data
public class DebtModel {
    private Long id;
    private UserModel fromUser;
    private UserModel toUser;
    private Double amount;
    private Long group_ID;
}
