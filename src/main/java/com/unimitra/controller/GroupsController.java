package com.unimitra.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.unimitra.utility.UnimitraConstants;

@RestController
@RequestMapping("/groups")
public class GroupsController {

	@Autowired
	GroupService groupService;

	private static final Logger LOGGER = LogManager.getLogger();

	@PostMapping("/createGroup")
	public ResponseEntity<String> createGroup(@RequestBody GroupEntity groupEntity) throws UnimitraException {
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + groupEntity.toString());
		return groupService.createGroup(groupEntity);
	}

	@GetMapping("/search")
	public List<GroupEntity> getListOfGroups(@RequestParam(required = false) String groupName,
			@RequestParam(required = false) String categoryName) throws UnimitraException {
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + "groupName {}, categoryName {}", groupName, categoryName);
		return groupService.getListofGroups(groupName, categoryName);

	}

	@GetMapping("/pending-requests")
	public List<GroupModel> getPendingGroupCreationRequests(@RequestParam int userId) throws UnimitraException {
		List<GroupModel> pendingRequestList = groupService.getPendingRequest(userId);
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + pendingRequestList.toString());
		return pendingRequestList;

	}

	@PostMapping("/decide-approval")

	public String decideGroupStatus(@RequestBody GroupEntity groupEntity,@RequestParam int userId) {
	  LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + groupEntity.toString());
    return groupService.decideGroupService(groupEntity,userId);
	}

	@PostMapping("/add-member")
	public String addMemberToGroup(@RequestParam int userIdToAdd, @RequestParam String groupName,@RequestParam int loggedInUserId) throws UnimitraException {
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + "userId {}, groupName {}, loggedInUserId {}", userIdToAdd, groupName,
				loggedInUserId);
    return groupService.addMemberToGroup(userIdToAdd, groupName, loggedInUserId);
	}

	@DeleteMapping("/deleteGroup")
	public ResponseEntity<String> deleteGroup(@RequestParam int groupId) throws UnimitraException {
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + "groupId {}", groupId);
		return groupService.deleteGroupbyGroupId(groupId);
	}
}
