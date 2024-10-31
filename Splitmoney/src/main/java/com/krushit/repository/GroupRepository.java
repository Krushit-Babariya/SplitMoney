package com.krushit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.krushit.entity.Group;
import com.krushit.entity.User;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMembersContainingAndActiveTrue(Integer memberId);
    
    @Query("SELECT g FROM Group g WHERE :userId MEMBER OF g.members")
    List<Group> findByUserJoinedGroups(@Param("userId") Integer userId);
    
    @Query("SELECT g.members FROM Group g WHERE g.id = :groupId")
    List<Long> findMembersByGroupId(@Param("groupId") Long groupId);
}
