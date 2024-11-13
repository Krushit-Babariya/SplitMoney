package com.krushit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.krushit.entity.Debt;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByFromUserAndToUserAndGroupIDAndActiveTrue(Long fromUser, Long toUser, Long groupID);
    List<Debt> findByFromUserAndGroupIDAndActiveTrue(Long fromUser, Long groupID);
    List<Debt> findByToUserAndGroupIDAndActiveTrue(Long toUser, Long groupID);
    List<Debt> findByGroupID(Long groupID);
    List<Debt> findByGroupIDAndFromUser(Long groupID, Long fromUser);
    List<Debt> findByGroupIDAndToUser(Long groupID, Long toUser);
    
    @Modifying
    @Query("DELETE FROM Debt d WHERE d.groupID = :groupId")
    void deleteAllByGroupID(@Param("groupId") Long groupId);

}
