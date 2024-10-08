package com.krushit.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "`groups`")
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

    @ElementCollection
    @CollectionTable(name = "group_members", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "member_id")
    private List<Integer> members;

    @ElementCollection
    @CollectionTable(name = "group_original_debts", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "debt_id")
    private List<Integer> originalDebts;

    @ElementCollection
    @CollectionTable(name = "group_simplified_debts", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "debt_id")
    private List<Integer> simplifiedDebts;

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
