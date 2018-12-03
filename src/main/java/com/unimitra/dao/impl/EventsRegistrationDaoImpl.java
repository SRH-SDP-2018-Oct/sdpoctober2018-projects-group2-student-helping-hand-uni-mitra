package com.unimitra.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.unimitra.dao.EventsRegistrationDao;
import com.unimitra.entity.EventsRegisterationEntity;

@Repository
public class EventsRegistrationDaoImpl implements EventsRegistrationDao {

	private SessionFactory sessionFactory;

	@Override
	public List<EventsRegisterationEntity> getUserByUserIdEventId(int userId, int eventId) {
		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("from EventsRegisterationEntity er where er.userId = '" + userId
				+ "' and er.eventId = '" + eventId + "'");
		List<EventsRegisterationEntity> eventRgistrationUser = query.getResultList();
		return eventRgistrationUser;
	}

	public EventsRegisterationEntity updateRegistrationFlag(EventsRegisterationEntity registerForEvent) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(registerForEvent);

		return registerForEvent;

	}

	@Override
	public void updateExsistingRegistrationFlag(EventsRegisterationEntity eventsRegistrationEntity1) {
		Session session = sessionFactory.getCurrentSession();
		session.update(eventsRegistrationEntity1);

	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
