package com.krushit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krushit.entity.User;
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
			return new ResponseEntity<GroupModel>(createdGroup, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error creating group", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/user/{userId}/joined")
	public ResponseEntity<?> getUserJoinedGroups(@PathVariable Integer userId) {
	    try {
	        List<GroupModel> userGroups = groupService.getUserJoinedGroups(userId);
	        System.out.println("User Groups :: " + userGroups);
	        return new ResponseEntity<>(userGroups, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>("Error retrieving user groups", HttpStatus.INTERNAL_SERVER_ERROR);
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
	
	@GetMapping("/getAllMembers/{groupId}")
	public ResponseEntity<?> getGroupMembers(@PathVariable Long groupId) {
	    try {
	        List<User> members = groupService.getGroupMembers(groupId);
	        if (members.isEmpty()) {
	            return new ResponseEntity<>("No members found in the group", HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(members, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>("Error retrieving group members", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	
//	@GetMapping("/group/{groupId}/user/{userId}")
//	public ResponseEntity<?> getExpensesByUserInGroup(@PathVariable Long groupId, @PathVariable Long userId) {
//	    try {
//	        List<ExpenseModel> expenses = expenseService.getExpensesByUserInGroup(groupId, userId);
//	        return new ResponseEntity<>(expenses, HttpStatus.OK);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return new ResponseEntity<>("Error retrieving expenses", HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
//	
//	@GetMapping("/group/{groupId}")
//	public ResponseEntity<?> getDebtsByGroupId(@PathVariable Long groupId) {
//	    try {
//	        List<DebtModel> debts = debtService.getDebtsByGroupId(groupId);
//	        return new ResponseEntity<>(debts, HttpStatus.OK);
//	    } catch (Exception e) {
//	        logger.error("Error fetching debts for group {}: {}", groupId, e.getMessage());
//	        return new ResponseEntity<>("Failed to fetch debts", HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
}
