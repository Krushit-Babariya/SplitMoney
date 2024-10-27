package com.krushit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.krushit.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByGroupIdAndActiveTrue(Long groupId);

    // This method should match the property name in the ExpenseUser entity
    List<Expense> findByUsers_FromUserIdAndActiveTrue(Long userId); 

    @Query("SELECT e FROM Expense e JOIN e.users u WHERE u.fromUserId = :userId OR u.toUserId = :userId")
    List<Expense> findAllExpensesByUserId(@Param("userId") Long userId);

    @Query("SELECT e FROM Expense e JOIN e.users u WHERE (u.fromUserId = :userId OR u.toUserId = :userId) AND e.groupId = :groupId")
    List<Expense> findAllExpensesByUserIdAndGroupId(@Param("userId") Long userId, @Param("groupId") Long groupId);

    @Query("SELECT e FROM Expense e WHERE e.createdBy.id = :userId")
    List<Expense> findAllByCreatedBy(@Param("userId") Long userId);
    
    @Query("SELECT e FROM Expense e WHERE e.createdBy.id = :userId AND e.groupId = :groupId")
    List<Expense> findAllByCreatedByAndGroupId(@Param("userId") Long userId, @Param("groupId") Long groupId);

    @Query("SELECT e FROM Expense e WHERE e.groupId = :groupId")
    List<Expense> findAllByGroupId(@Param("groupId") Long groupId);
}
