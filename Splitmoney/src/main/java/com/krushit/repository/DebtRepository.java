package com.krushit.repository;

import com.krushit.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByFromUserAndToUserAndGroupIDAndActiveTrue(Long fromUser, Long toUser, Long groupID);
    List<Debt> findByFromUserAndGroupIDAndActiveTrue(Long fromUser, Long groupID);
    List<Debt> findByToUserAndGroupIDAndActiveTrue(Long toUser, Long groupID);
}
