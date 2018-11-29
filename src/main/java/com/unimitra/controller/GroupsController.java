package com.unimitra.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unimitra.model.GroupModel;

@RestController
@RequestMapping("/groups")
public class GroupsController {

	@PostMapping("/create")
	public String createGroup(@RequestBody GroupModel groupModel) {
		return "Posted Successfully";
	}

	@GetMapping("/search")
	public List<GroupModel> getListOfGroups(@RequestParam(required = false) String groupName,
			@RequestParam(required = false) String category) {
		return new ArrayList<GroupModel>();
	}

	@GetMapping("/pending-requests")
	public List<GroupModel> getPendingGroupCreationRequests(@RequestParam(required = false) int userId) {
		return new ArrayList<GroupModel>();
	}

	@PostMapping("/decide-approval")
	public String decideGroupStatus(@RequestParam int groupId, @RequestParam int approvedByUserId,
			@RequestParam String approvalStatus) {
		return "Approved/Rejected";
	}

	@PostMapping("/add-member")
	public String addMemberToGroup(@RequestParam int userId, @RequestParam int groupId) {
		return "Added successful";
	}

	@DeleteMapping("/delete-member")
	public String deleteMemberFromGroup(@RequestParam int userId, @RequestParam int groupId,
			@RequestParam boolean isMemberActive) {
		return "Delete successful";
	}
}
