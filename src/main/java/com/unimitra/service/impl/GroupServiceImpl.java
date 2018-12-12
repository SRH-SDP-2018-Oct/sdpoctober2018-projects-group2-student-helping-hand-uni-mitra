package com.unimitra.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.unimitra.dao.GroupDao;
import com.unimitra.entity.CategoryEntity;
import com.unimitra.entity.GroupEntity;
import com.unimitra.entity.GroupMemberEntity;
import com.unimitra.exception.ErrorCodes;
import com.unimitra.exception.UnimitraException;
import com.unimitra.model.GroupModel;
import com.unimitra.service.GroupService;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	GroupDao groupDao;

	@Override
	public String decideGroupService(GroupEntity groupEntity,int userId) {

		return groupDao.decideGroupStatus(groupEntity,userId);

	}

	@Override
	public String addMemberToGroup(int userId, String groupName, int loggedInUserId) throws UnimitraException {
		GroupEntity groupsData = groupDao.getGroupIdData(groupName);
		checkIfUserIsAdmin(groupsData, loggedInUserId);

		GroupMemberEntity groupMemberEntity = new GroupMemberEntity();

		Timestamp time = new Timestamp(System.currentTimeMillis());
		groupMemberEntity.setMemberUserId(userId);
		groupMemberEntity.setMemberGroupId(groupsData.getGroupId());
		groupMemberEntity.setGroupMemberIsActive(true);
		groupMemberEntity.setGroupMemberCreationDateTime(time);
		return groupDao.addMemberToGroupData(groupMemberEntity);

	}

	private void checkIfUserIsAdmin(GroupEntity groupsData, int loggedInUserId) throws UnimitraException {
		if (groupsData.getGroupCreatedByUserId() != loggedInUserId) {
			throw new UnimitraException(ErrorCodes.USER_HAS_NO_ACCESS_TO_ADD_MEMBER_TO_GROUP);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupModel> getPendingRequest(int userId) throws UnimitraException {
		return (List<GroupModel>) groupDao.getPendingRequestdata(userId);
	}

	public ResponseEntity<String> createGroup(GroupEntity groupEntity) throws UnimitraException {

		String groupName = groupEntity.getGroupName();

		List<GroupEntity> groupByName = groupDao.getGroupListByGroupName(groupName);

		if (!(ObjectUtils.isEmpty(groupByName))) {
			throw new UnimitraException(ErrorCodes.DUPLICATE_GROUP_NAME);
		}

		Timestamp time = new Timestamp(System.currentTimeMillis());
		groupEntity.setGroupCreationDatetime(time);

		groupDao.createGroup(groupEntity);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteGroupbyGroupId(int groupId) throws UnimitraException {

		groupDao.deleteGroupbyGroupId(groupId);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Override
	public List<GroupEntity> getListofGroups(String groupName, String categoryName) throws UnimitraException {
		List<GroupEntity> listOfGroups;

		if (ObjectUtils.isEmpty(groupName)) {
			List<CategoryEntity> categoryEntityList = groupDao.getListOfCategoryByCategoryName(categoryName);
			List<Integer> categoryIdList = new ArrayList<Integer>();
			if (!(ObjectUtils.isEmpty(categoryEntityList))) {

				categoryEntityList.forEach(category -> {
					categoryIdList.add(category.getCategoryId());

				});
				listOfGroups = groupDao.getGroupListByCategoryIds(categoryIdList);
				return listOfGroups;

			}

		}
		listOfGroups = groupDao.getGroupListByGroupName(groupName);

		return listOfGroups;

	}

}
