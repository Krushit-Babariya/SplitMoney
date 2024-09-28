package com.krushit.service;

import com.krushit.entity.Group;
import com.krushit.entity.User;

import java.util.List;
import java.util.Optional;

public interface IGroupService {

    Group createGroup(Group group);

    Group updateGroup(Group group);

    Optional<Group> getGroupById(Long id);

    void deleteGroup(Long id);

    Group addMemberToGroup(Long groupId, Long userId);

    Group removeMemberFromGroup(Long groupId, Long userId);

    List<Group> getAllGroups();
}
