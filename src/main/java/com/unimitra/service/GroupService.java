package com.unimitra.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.unimitra.entity.GroupEntity;
import com.unimitra.exception.UnimitraException;

public interface GroupService {

	ResponseEntity<String> createGroup(GroupEntity groupEntity) throws UnimitraException;

	ResponseEntity<String> deleteGroupbyGroupId(int groupId) throws UnimitraException;

	List<GroupEntity> getListofGroups(String groupName, String categoryName) throws UnimitraException;


}
