package com.krushit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.krushit.model.GroupModel;
import com.krushit.service.IGroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

	@Autowired
	private IGroupService groupService;

	@PostMapping("/createGroup")
	public ResponseEntity<?> createGroup(@RequestBody GroupModel group) {
		try {
			GroupModel createdGroup = groupService.createGroup(group);
			return new ResponseEntity<GroupModel>(createdGroup, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error creating group", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/updateGroup/{id}")
	public ResponseEntity<?> updateGroupPartial(@PathVariable Long id, @RequestBody GroupModel groupUpdates) {
		try {
			GroupModel updatedGroup = groupService.updateGroup(id, groupUpdates);
			return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error updating group", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getGroup/{id}")
	public ResponseEntity<?> getGroupById(@PathVariable Long id) {
		try {
			Optional<GroupModel> group = groupService.getGroupById(id);
			return new ResponseEntity<Optional<GroupModel>>(group, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error retrieving group", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteGroup/{id}")
	public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
		try {
			groupService.deleteGroup(id);
			return new ResponseEntity<String>("Group deleted successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error deleting group", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/addMember/{groupId}/members/{userId}")
	public ResponseEntity<?> addMemberToGroup(@PathVariable Long groupId, @PathVariable Integer userId) {
		try {
			GroupModel updatedGroup = groupService.addMemberToGroup(groupId, userId);
			return new ResponseEntity<GroupModel>(updatedGroup, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error adding member to group", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/removeMember/{groupId}/members/{userId}")
	public ResponseEntity<?> removeMemberFromGroup(@PathVariable Long groupId, @PathVariable Integer userId) {
		try {
			GroupModel updatedGroup = groupService.removeMemberFromGroup(groupId, userId);
			return new ResponseEntity<GroupModel>(updatedGroup, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error removing member from group", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllGroups")
	public ResponseEntity<?> getAllGroups() {
		try {
			List<GroupModel> groups = groupService.getAllGroups();
			return new ResponseEntity<List<GroupModel>>(groups, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error retrieving groups", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
