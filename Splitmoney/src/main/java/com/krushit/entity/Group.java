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
@Table(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(nullable = false)
    private String groupType;

    @Column(name = "simplify_by_default")
    private boolean simplifyByDefault;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> members;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Debt> originalDebts;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Debt> simplifiedDebts;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] avatar;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] coverPhoto;

    private String inviteLink;

    @Column(nullable = false)
    private boolean active = true;
}
