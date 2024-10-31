package com.krushit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krushit.entity.Debt;
import com.krushit.model.DebtModel;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByFromUserAndToUserAndGroupIDAndActiveTrue(Long fromUser, Long toUser, Long groupID);
    List<Debt> findByFromUserAndGroupIDAndActiveTrue(Long fromUser, Long groupID);
    List<Debt> findByToUserAndGroupIDAndActiveTrue(Long toUser, Long groupID);
    List<Debt> findByGroupID(Long groupID);
}
