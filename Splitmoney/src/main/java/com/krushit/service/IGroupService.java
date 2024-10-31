package com.krushit.service;

import java.util.List;
import java.util.Optional;

import com.krushit.entity.User;
import com.krushit.model.GroupModel;
import com.krushit.model.UserModel;

public interface IGroupService {

    // Create a new group
    GroupModel createGroup(GroupModel groupModel);

    // Update an existing group
    GroupModel updateGroup(Long id, GroupModel groupModel);
    
    List<GroupModel> getUserJoinedGroups(Integer userId);

    // Retrieve a group by its ID
    Optional<GroupModel> getGroupById(Long id);

    // Delete a group by its ID
    void deleteGroup(Long id);

    // Add a member to a group by their userId
    GroupModel addMemberToGroup(Long groupId, Integer userId);

    // Remove a member from a group by their userId
    GroupModel removeMemberFromGroup(Long groupId, Integer userId);

    // Retrieve all groups
    List<GroupModel> getAllGroups();
    
    List<User> findMembersByGroupId(Long groupId);

	List<User> getGroupMembers(Long groupId);

}
