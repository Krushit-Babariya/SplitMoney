package com.krushit.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double cost;

    @Column(length = 500)
    private String description;

    private String details;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "repeat_interval")
    private String repeatInterval;

    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "split_equally")
    private boolean splitEqually;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "expense_id")
    private List<Repayment> repayments;

    @ElementCollection
    @CollectionTable(name = "expense_users", joinColumns = @JoinColumn(name = "expense_id"))
    private List<ExpenseUser> users;

    @Column(nullable = false)
    private boolean active = true;
}

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Repayment {

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private User toUser;

    @Column(name = "amount", nullable = false)
    private Double amount;
}

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ExpenseUser {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "paid_share")
    private Double paidShare;

    @Column(name = "owed_share")
    private Double owedShare;

    @Column(name = "net_balance")
    private Double netBalance;
}
