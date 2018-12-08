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
import org.springframework.util.ObjectUtils;

import com.unimitra.dao.GroupDao;
import com.unimitra.entity.CategoryEntity;
import com.unimitra.entity.GroupEntity;
import com.unimitra.entity.GroupMemberEntity;
import com.unimitra.exception.ErrorCodes;
import com.unimitra.exception.UnimitraException;

@Repository
public class GroupDaoImpl implements GroupDao {

	SessionFactory sessionFactory;
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
		nullCheckForEntity(deleteGroupByGroupId, ErrorCodes.GROUP_NOT_PRESENT_FOR_GROUPID);

		deleteGroupByGroupId.setGroupIsActive(flase);
		session.update(deleteGroupByGroupId);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Override
	public boolean checkIfUserHasAccessToGroup(int userId, int groupId) throws UnimitraException {
		// TODO Add this method in Group DAO
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

	private void nullCheckForEntity(Object entity, String errorCode) throws UnimitraException {
		if (ObjectUtils.isEmpty(entity)) {
			throw new UnimitraException(errorCode);
		}
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
