package com.unimitra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unimitra.exception.UnimitraException;
import com.unimitra.model.GroupModel;

@Service
public interface GroupService {

	String addMemberToGroup(int userId, String groupName) throws UnimitraException;

	List<GroupModel> getPendingRequest(int userId) throws UnimitraException;

}
