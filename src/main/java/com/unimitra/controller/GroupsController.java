package com.unimitra.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unimitra.exception.UnimitraException;
import com.unimitra.model.GroupModel;
import com.unimitra.service.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupsController {

	@Autowired
	GroupService groupService;

	@PostMapping("/create")
	public String createGroup(@RequestBody GroupModel groupModel) {
		return "Posted Successfully";
	}

	@GetMapping("/search")
	public List<GroupModel> getListOfGroups(@RequestParam(required = false) String groupName,
			@RequestParam(required = false) String category) {
		return new ArrayList<>();
	}

	@GetMapping("/pending-requests")
	public List<GroupModel> getPendingGroupCreationRequests(@RequestParam int userId) throws UnimitraException {
		List<GroupModel> pendingRequestList = groupService.getPendingRequest(userId);
		return pendingRequestList;

	}

	@PostMapping("/decide-approval")
	public String decideGroupStatus(@RequestParam int groupId, @RequestParam int approvedByUserId,
			@RequestParam String approvalStatus) {
		return "Approved/Rejected";
	}

	@PostMapping("/add-member")
	public String addMemberToGroup(@RequestParam int userId, @RequestParam String groupName) throws UnimitraException {
		return groupService.addMemberToGroup(userId, groupName);
	}

	@DeleteMapping("/delete-member")
	public String deleteMemberFromGroup(@RequestParam int userId, @RequestParam int groupId,
			@RequestParam boolean isMemberActive) {
		return "Delete successful";
	}
}
