package com.unimitra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unimitra.entity.GroupEntity;
import com.unimitra.exception.UnimitraException;
import com.unimitra.model.GroupModel;
import com.unimitra.service.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupsController {

@Autowired
GroupService groupService;


	@PostMapping("/createGroup")
	public ResponseEntity<String> createGroup(@RequestBody GroupEntity groupEntity) throws UnimitraException {
		return groupService.createGroup(groupEntity);
	}

	@GetMapping("/search")
	public List<GroupEntity> getListOfGroups(@RequestParam(required = false) String groupName,
			@RequestParam(required = false) String categoryName) throws UnimitraException {
		return groupService.getListofGroups(groupName, categoryName);

	}

	@GetMapping("/pending-requests")
	public List<GroupModel> getPendingGroupCreationRequests(@RequestParam int userId) throws UnimitraException {
		List<GroupModel> pendingRequestList = groupService.getPendingRequest(userId);
		return pendingRequestList;

	}

	@PostMapping("/decide-approval")
	public String decideGroupStatus(@RequestBody GroupEntity groupEntity,@RequestParam int userId) {
		return groupService.decideGroupService(groupEntity,userId);
	}

	@PostMapping("/add-member")
	public String addMemberToGroup(@RequestParam int userIdToAdd, @RequestParam String groupName,@RequestParam int loggedInUserId) throws UnimitraException {
		return groupService.addMemberToGroup(userIdToAdd, groupName, loggedInUserId);
	}

	@DeleteMapping("/deleteGroup")
	public ResponseEntity<String> deleteGroup(@RequestParam int groupId) throws UnimitraException {
		return groupService.deleteGroupbyGroupId(groupId);
	}
}
