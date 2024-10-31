package com.krushit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krushit.entity.Group;
import com.krushit.entity.User;
import com.krushit.model.GroupModel;
import com.krushit.repository.GroupRepository;
import com.krushit.repository.UserRepository;

@Service
public class GroupServiceImpl implements IGroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public GroupModel createGroup(GroupModel groupModel) {
		Group group = new Group();
		BeanUtils.copyProperties(groupModel, group);
		group = groupRepository.save(group);
		GroupModel model = new GroupModel();
		BeanUtils.copyProperties(group, model);
		return model;
	}

	@Override
	public List<GroupModel> getUserJoinedGroups(Integer userId) {
		List<Group> groups = groupRepository.findAll();
		return groups.stream().filter(group -> group.getMembers().contains(userId)).map(this::convertToModel)
				.collect(Collectors.toList());
	}

	private GroupModel convertToModel(Group group) {
		GroupModel model = new GroupModel();
		model.setId(group.getId());
		model.setName(group.getName());
		model.setGroupType(group.getGroupType());
		model.setSimplifyByDefault(group.isSimplifyByDefault());
		model.setMembers(group.getMembers());
		model.setOriginalDebts(group.getOriginalDebts());
		model.setSimplifiedDebts(group.getSimplifiedDebts());
		model.setAvatar(group.getAvatar());
		model.setCoverPhoto(group.getCoverPhoto());
		model.setInviteLink(group.getInviteLink());
		return model;
	}

	@Override
	public GroupModel updateGroup(Long id, GroupModel groupModel) {
		// Fetch the existing group by ID
		Group existingGroup = groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Group not found"));

		if (groupModel.getName() != null) {
			existingGroup.setName(groupModel.getName());
		}
		if (groupModel.getGroupType() != null) {
			existingGroup.setGroupType(groupModel.getGroupType());
		}
		if (groupModel.isSimplifyByDefault() != existingGroup.isSimplifyByDefault()) {
			existingGroup.setSimplifyByDefault(groupModel.isSimplifyByDefault());
		}
		if (groupModel.getAvatar() != null) {
			existingGroup.setAvatar(groupModel.getAvatar());
		}
		if (groupModel.getCoverPhoto() != null) {
			existingGroup.setCoverPhoto(groupModel.getCoverPhoto());
		}

		existingGroup = groupRepository.save(existingGroup);

		GroupModel updatedModel = new GroupModel();
		BeanUtils.copyProperties(existingGroup, updatedModel);
		return updatedModel;
	}

	@Override
	public Optional<GroupModel> getGroupById(Long id) {
		Optional<Group> group = groupRepository.findById(id);
		return group.map(g -> {
			GroupModel model = new GroupModel();
			BeanUtils.copyProperties(g, model);
			return model;
		});
	}

	@Override
	public void deleteGroup(Long id) {
		groupRepository.deleteById(id);
	}

	@Override
	public GroupModel addMemberToGroup(Long groupId, Integer userId) {
		Optional<Group> groupOpt = groupRepository.findById(groupId);

		if (groupOpt.isPresent()) {
			Group group = groupOpt.get();
			if (!group.getMembers().contains(userId)) {
				group.getMembers().add(userId); // Add the user ID as an Integer
			}
			group = groupRepository.save(group);

			GroupModel model = new GroupModel();
			BeanUtils.copyProperties(group, model);
			return model;
		}
		throw new RuntimeException("Group not found");
	}

	@Override
	public GroupModel removeMemberFromGroup(Long groupId, Integer userId) {
		Optional<Group> groupOpt = groupRepository.findById(groupId);

		if (groupOpt.isPresent()) {
			Group group = groupOpt.get();
			group.getMembers().remove(userId); // Remove the user ID as an Integer
			group = groupRepository.save(group);

			GroupModel model = new GroupModel();
			BeanUtils.copyProperties(group, model);
			return model;
		}
		throw new RuntimeException("Group not found");
	}

	public List<GroupModel> getAllGroups() {
		return groupRepository.findAll().stream().map(group -> {
			GroupModel model = new GroupModel();
			BeanUtils.copyProperties(group, model);
			return model;
		}).collect(Collectors.toList());
	}

	@Override
	public List<User> getGroupMembers(Long groupId) {
	    System.out.println("Group ID :: " + groupId);
	    
	    // Get the list of user IDs from the group
	    List<Long> memberIds = groupRepository.findMembersByGroupId(groupId);
	    
	    // Retrieve the User objects for these IDs
	    List<User> members = new ArrayList<>();
	    
	    for (Long userId : memberIds) {
	        Optional<User> userOptional = userRepository.findById(userId);
	        userOptional.ifPresent(members::add); 
	        System.out.println("Members :: " + userOptional);
	    }
	    
	    return members;
	}




	@Override
	public List<User> findMembersByGroupId(Long groupId) {
		// TODO Auto-generated method stub
		return null;
	}
};
