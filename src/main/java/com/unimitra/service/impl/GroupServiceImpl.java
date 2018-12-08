package com.unimitra.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimitra.dao.GroupDao;
import com.unimitra.entity.GroupMemberEntity;
import com.unimitra.exception.UnimitraException;
import com.unimitra.model.GroupModel;
import com.unimitra.service.GroupService;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	GroupDao groupDao;

	@Override
	public String addMemberToGroup(int userId, String groupName) throws UnimitraException {
		List<Integer> groupId = groupDao.getGroupIdData(groupName);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		GroupMemberEntity groupMemberEntity = new GroupMemberEntity();
		groupMemberEntity.setMemberUserId(userId);
		groupMemberEntity.setMemberGroupId(groupId.get(0));
		groupMemberEntity.setGroupMemberIsActive(true);
		groupMemberEntity.setGroupMemberCreationDateTime(time);
		return groupDao.addMemberToGroupData(groupMemberEntity);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupModel> getPendingRequest(int userId) throws UnimitraException {
		return (List<GroupModel>) groupDao.getPendingRequestdata(userId);
	}

}
