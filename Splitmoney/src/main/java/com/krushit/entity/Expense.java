package com.krushit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL)
    private List<ExpenseUser> users;

    @Column(nullable = false)
    private boolean active = true;
}
