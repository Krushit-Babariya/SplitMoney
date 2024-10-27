package com.krushit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "debts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Debt extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long fromUser;
    private Long toUser;

    @Column(name = "amount", nullable = false)
    private Double amount;

    private Long groupID; 

    @Column(nullable = false)
    private boolean active = true;
}
