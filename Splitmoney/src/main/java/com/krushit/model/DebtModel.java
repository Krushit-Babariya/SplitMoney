package com.krushit.model;

import lombok.Data;

@Data
public class DebtModel {
    private Long id;
    private UserModel fromUser;
    private UserModel toUser;
    private Double amount;
    private String currencyCode;
    private GroupModel group;
    private boolean active = true;
}
