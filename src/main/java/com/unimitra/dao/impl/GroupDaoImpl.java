package com.unimitra.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.unimitra.dao.GroupDao;
import com.unimitra.entity.CategoryEntity;
import com.unimitra.entity.GroupEntity;
import com.unimitra.entity.GroupMemberEntity;
import com.unimitra.exception.ErrorCodes;
import com.unimitra.exception.UnimitraException;
import com.unimitra.utility.UnimitraUtility;

@Repository
public class GroupDaoImpl implements GroupDao {
	SessionFactory sessionFactory;

	@Override
	public GroupEntity getGroupIdData(String groupName) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<GroupEntity> groupData = session.createQuery("from GroupEntity where groupName=:Name and groupIsActive = true")
				.setParameter("Name", groupName).list();
		UnimitraUtility.nullCheckForEntity(groupData, ErrorCodes.GROUP_NOT_PRESENT);
		return groupData.get(0);
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
		UnimitraUtility.nullCheckForEntity(groupEntity, ErrorCodes.NO_GROUP_APPROVAL_REQUEST);
		return groupEntity;
	}

	private boolean flase;

	@Override
	public GroupEntity createGroup(GroupEntity groupEntity) {

		Session session = sessionFactory.getCurrentSession();
		session.save(groupEntity);
		return groupEntity;
	}

	@Override
	public ResponseEntity<String> deleteGroupbyGroupId(int groupId) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		GroupEntity deleteGroupByGroupId = session.get(GroupEntity.class, groupId);
		UnimitraUtility.nullCheckForEntity(deleteGroupByGroupId, ErrorCodes.GROUP_NOT_PRESENT_FOR_GROUPID);

		deleteGroupByGroupId.setGroupIsActive(flase);
		session.update(deleteGroupByGroupId);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Override
	public boolean checkIfUserHasAccessToGroup(int userId, int groupId) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<GroupMemberEntity> criteriaQuery = criteriaBuilder.createQuery(GroupMemberEntity.class);
		Root<GroupMemberEntity> groupRoot = criteriaQuery.from(GroupMemberEntity.class);
		criteriaQuery.select(groupRoot);

		criteriaQuery.where(criteriaBuilder.equal(groupRoot.get("memberGroupId"), groupId),
				criteriaBuilder.equal(groupRoot.get("memberUserId"), userId));

		List<GroupMemberEntity> groupMemberEntity = session.createQuery(criteriaQuery).getResultList();
		return !CollectionUtils.isEmpty(groupMemberEntity);
	}

	@Override
	public List<CategoryEntity> getListOfCategoryByCategoryName(String categoryName) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<CategoryEntity> criteriaQuery = builder.createQuery(CategoryEntity.class);
		EntityType<CategoryEntity> type = session.getMetamodel().entity(CategoryEntity.class);
		Root<CategoryEntity> root = criteriaQuery.from(CategoryEntity.class);
		criteriaQuery.select(root);

		criteriaQuery.where(
				builder.like(builder.lower(root.get(type.getDeclaredSingularAttribute("categoryName", String.class))),
						"%" + categoryName.toLowerCase() + "%"));

		return session.createQuery(criteriaQuery).getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupEntity> getGroupListByCategoryIds(List<Integer> categoryListIds) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from GroupEntity where groupCategoryId in (:groupCategoryId)";
		Query query = session.createQuery(hql);
		query.setParameter("groupCategoryId", categoryListIds);
		return query.getResultList();
	}

	@Override
	public List<GroupEntity> getGroupListByGroupName(String groupName) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<GroupEntity> criteriaQuery = builder.createQuery(GroupEntity.class);
		EntityType<GroupEntity> type = session.getMetamodel().entity(GroupEntity.class);
		Root<GroupEntity> root = criteriaQuery.from(GroupEntity.class);
		criteriaQuery.select(root);

		criteriaQuery.where(
				builder.like(builder.lower(root.get(type.getDeclaredSingularAttribute("groupName", String.class))),
						"%" + groupName.toLowerCase() + "%"));

		return session.createQuery(criteriaQuery).getResultList();

	}

	@Override
	public int getGroupIdFromGroupName(String groupName) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<GroupEntity> criteriaQuery = criteriaBuilder.createQuery(GroupEntity.class);
		Root<GroupEntity> groupRoot = criteriaQuery.from(GroupEntity.class);
		criteriaQuery.select(groupRoot);
		criteriaQuery.where(criteriaBuilder.equal(groupRoot.get("groupName"), groupName));
		List<GroupEntity> groupMemberEntity = session.createQuery(criteriaQuery).getResultList();
		if (CollectionUtils.isEmpty(groupMemberEntity)) {
			throw new UnimitraException(ErrorCodes.GROUP_DOES_NOT_EXIST);
		}
		return groupMemberEntity.get(0).getGroupId();
	}

	@Override
	public String decideGroupStatus(GroupEntity groupEntity,int userId) {

		Session session = sessionFactory.getCurrentSession();
		GroupEntity updateGroupData = session.get(GroupEntity.class, groupEntity.getGroupId());
		if (updateGroupData.getGroupApprovalStatus().equals("Pending") && updateGroupData.getGroupApprovalByUserId()== userId ) {
			updateGroupData.setGroupApprovalStatus(groupEntity.getGroupApprovalStatus());
			session.update(updateGroupData);
			return "Group Status Updated";
		}
		return "group is already approved or you dont have the permission to approve the group.";

	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
