package com.unimitra.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.unimitra.dao.UserDetailsDao;
import com.unimitra.entity.UserDetailsEntity;

@Repository
public class UserDetailsDaoImpl implements UserDetailsDao {

	SessionFactory sessionFactory;

	@Override
	public UserDetailsEntity getUserDetails(int userId) {
		Session session = sessionFactory.getCurrentSession();
		UserDetailsEntity userDetailEntity = new UserDetailsEntity();
		userDetailEntity = session.get(UserDetailsEntity.class, userId);
		return userDetailEntity;
	}

	@Override
	public List<?> getUserAnswerDetails(int userId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<?>) session
				.createQuery("select a.answerDescription,a.answerStatus \r\n"
						+ "from UserDetailsEntity ud,AnswersEntity a \r\n"
						+ "where ud.userId = a.answerPostedByUserId and ud.userId =: userIdForQuery")
				.setParameter("userIdForQuery", userId).list();
	}

	@Override
	public List<?> getUserQuestionDetails(int userId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<?>) session
				.createQuery("select q.questionDescription \r\n" + "from UserDetailsEntity ud,QuestionsEntity q \r\n"
						+ "where ud.userId = q.questionPostedByUserId and ud.userId =: userIdForQuery")
				.setParameter("userIdForQuery", userId).list();

	}

	@Override
	public List<?> getUserEventDetails(int userId) {
		Session session = sessionFactory.getCurrentSession();
		return (List<?>) session.createQuery("select e.eventName \r\n"
				+ "from UserDetailsEntity ud,EventsEntity e,EventsRegisterationEntity ev \r\n"
				+ "where  e.eventId = ev.eventId and \r\n"
				+ "ev.userId = ud.userId  and ud.userId =: userIdForQuery and ev.eventRegistrationFlag= true and "
				+ "e.eventIsActive = true").setParameter("userIdForQuery", userId).list();
	}

	@Override
	public List<?> getUserGroupDetails(int userId) {
		Session session = sessionFactory.getCurrentSession();
		List<?> list = (List<?>) session.createQuery("select g.groupName\r\n"
				+ "from UserDetailsEntity ud,GroupEntity g,GroupMemberEntity gm\r\n" + "where\r\n"
				+ "gm.memberGroupId = g.groupId and\r\n"
				+ "gm.memberUserId = ud.userId and ud.userId = : userIdForQuery and gm.groupMemberIsActive = true and g.groupApprovalStatus = 'Approved'")
				.setParameter("userIdForQuery", userId).list();
		return list;

	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
