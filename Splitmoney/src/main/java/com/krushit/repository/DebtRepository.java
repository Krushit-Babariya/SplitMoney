package com.krushit.repository;

import com.krushit.entity.Debt;
import com.krushit.entity.Group;
import com.krushit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByFromUserAndToUserAndGroupAndActiveTrue(User fromUser, User toUser, Group group);
    List<Debt> findByFromUserAndGroupAndActiveTrue(User fromUser, Group group);
    List<Debt> findByToUserAndGroupAndActiveTrue(User toUser, Group group);
}
