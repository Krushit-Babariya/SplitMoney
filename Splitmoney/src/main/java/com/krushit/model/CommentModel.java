package com.krushit.model;

import lombok.Data;

@Data
public class CommentModel {
    private Long id;
    private String content;
    private String commentType = "User";
    private ExpenseModel expense;
    private UserModel user;
    private boolean active = true;
}
