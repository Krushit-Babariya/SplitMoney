package com.krushit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.krushit.entity.ExpenseUser;

@Repository
public interface ExpenseUserRepository extends JpaRepository<ExpenseUser, Long> {

    @Query("SELECT eu FROM ExpenseUser eu JOIN eu.expense e WHERE e.groupId = :groupId AND e.active = true")
    List<ExpenseUser> findAllByGroupId(@Param("groupId") Long groupId);
}
