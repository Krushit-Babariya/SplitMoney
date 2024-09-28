package com.krushit.repository;

import com.krushit.entity.Group;
import com.krushit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMembersContainingAndActiveTrue(User member);
}
