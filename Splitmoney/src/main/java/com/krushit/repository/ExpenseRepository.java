package com.krushit.repository;

import com.krushit.entity.Expense;
import com.krushit.entity.Group;
import com.krushit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByGroupAndActiveTrue(Group group);
    List<Expense> findByUsers_UserAndActiveTrue(User user);  // To find expenses a user is involved in
}
