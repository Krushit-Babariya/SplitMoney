package com.krushit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String firstName;

    @Column(length = 20, nullable = false)
    private String lastName;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] picture;

    @Column(nullable = false)
    private boolean customPicture;

    @Column(nullable = false)
    private String defaultCurrency;

    @Column(nullable = false)
    private String locale;

    @Column(nullable = false)
    private boolean activeSW = false;

    @Column(nullable = false)
    private boolean active = true;
}
