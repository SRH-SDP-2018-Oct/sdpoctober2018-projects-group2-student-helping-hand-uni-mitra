package com.unimitra.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.unimitra.entity.GroupEntity;
import com.unimitra.exception.UnimitraException;
import com.unimitra.model.GroupModel;

@Service
public interface GroupService {


	String decideGroupService(GroupEntity groupEntity);
	String addMemberToGroup(int userId, String groupName) throws UnimitraException;

	List<GroupModel> getPendingRequest(int userId) throws UnimitraException;

	ResponseEntity<String> createGroup(GroupEntity groupEntity) throws UnimitraException;

	ResponseEntity<String> deleteGroupbyGroupId(int groupId) throws UnimitraException;

	List<GroupEntity> getListofGroups(String groupName, String categoryName) throws UnimitraException;

}
