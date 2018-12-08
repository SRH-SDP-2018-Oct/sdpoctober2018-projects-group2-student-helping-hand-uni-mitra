package com.unimitra.dao;

import java.util.List;

import com.unimitra.entity.GroupMemberEntity;
import com.unimitra.exception.UnimitraException;

public interface GroupDao {

	List<Integer> getGroupIdData(String groupName) throws UnimitraException;

	String addMemberToGroupData(GroupMemberEntity groupMemberEntity);

	List<?> getPendingRequestdata(int userId) throws UnimitraException;

}
