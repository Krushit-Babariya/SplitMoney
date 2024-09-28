package com.krushit.repository;

import com.krushit.entity.Comment;
import com.krushit.entity.Expense;
import com.krushit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByExpenseAndActiveTrue(Expense expense);
    List<Comment> findByUserAndActiveTrue(User user);
}
