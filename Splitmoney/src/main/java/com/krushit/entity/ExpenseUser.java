package com.krushit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "expense_users")
public class ExpenseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "from_user_id", nullable = false) 
    private Long fromUserId;
    
    @Column(name = "to_user_id", nullable = false) 
    private Long toUserId; 

    @Column(name = "owed_share")
    private Double owedShare;

    @Column(name = "net_balance")
    private Double netBalance;
    
    @ManyToOne 
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense; 
}
