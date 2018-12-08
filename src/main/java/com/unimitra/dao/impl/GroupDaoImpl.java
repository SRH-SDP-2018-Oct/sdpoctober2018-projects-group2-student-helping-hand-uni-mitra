package com.unimitra.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.unimitra.dao.GroupDao;
import com.unimitra.entity.GroupEntity;
import com.unimitra.entity.GroupMemberEntity;
import com.unimitra.exception.ErrorCodes;
import com.unimitra.exception.UnimitraException;

@Repository
public class GroupDaoImpl implements GroupDao {
	SessionFactory sessionFactory;

	@Override
	public List<Integer> getGroupIdData(String groupName) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Integer> groupId = session.createQuery("select groupId from GroupEntity where groupName=:Name")
				.setParameter("Name", groupName).list();
		nullCheckForEntity(groupId, ErrorCodes.GROUP_NOT_PRESET);
		return groupId;
	}

	@Override
	public String addMemberToGroupData(GroupMemberEntity groupMemberEntity) {
		Session session = sessionFactory.getCurrentSession();
		List<?> isRecordExsist = session
				.createQuery("from GroupMemberEntity where memberUserId=: userId and memberGroupId =: memberId")
				.setParameter("userId", groupMemberEntity.getMemberUserId())
				.setParameter("memberId", groupMemberEntity.getMemberGroupId()).list();

		if (!isRecordExsist.isEmpty()) {
			return "user already present in the group";

		}
		session.save(groupMemberEntity);
		return "success";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupEntity> getPendingRequestdata(int userId) throws UnimitraException {

		Session session = sessionFactory.getCurrentSession();
		List<GroupEntity> groupEntity;
		groupEntity = session.createQuery(
				"from GroupEntity where groupApprovalByUserId =: userIdForQuery and groupApprovalStatus=: statusForQuery")
				.setParameter("userIdForQuery", userId).setParameter("statusForQuery", "Pending").list();
		nullCheckForEntity(groupEntity, ErrorCodes.NO_GROUP_APPROVAL_REQUEST);
		return groupEntity;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private void nullCheckForEntity(List<Integer> groupId, String errorCode) throws UnimitraException {
		if (ObjectUtils.isEmpty(groupId)) {
			throw new UnimitraException(errorCode);
		}
	}

	private void nullCheckForEntity(Object entity, String errorCode) throws UnimitraException {
		if (ObjectUtils.isEmpty(entity)) {
			throw new UnimitraException(errorCode);
		}
	}
}
