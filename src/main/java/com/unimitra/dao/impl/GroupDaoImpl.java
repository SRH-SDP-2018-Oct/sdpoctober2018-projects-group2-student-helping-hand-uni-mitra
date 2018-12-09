package com.unimitra.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.unimitra.dao.GroupDao;
import com.unimitra.entity.GroupEntity;

@Repository
public class GroupDaoImpl implements GroupDao {

	SessionFactory sessionFactory;
	
	@Override
	public String decideGroupStatus(GroupEntity groupEntity) {
	
		
		Session session = sessionFactory.getCurrentSession();
		GroupEntity updateGroupData = session.get(GroupEntity.class, groupEntity.getGroupId());
		if (updateGroupData.getGroupApprovalStatus().equals("Pending")) {
			updateGroupData.setGroupApprovalStatus(groupEntity.getGroupApprovalStatus());
			session.update(updateGroupData);
			return "Group Status Updated";
		}
			return "Action already taken";
	
	}

	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
