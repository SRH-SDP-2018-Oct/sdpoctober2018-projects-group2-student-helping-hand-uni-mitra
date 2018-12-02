package com.unimitra.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.unimitra.dao.EventDao;
import com.unimitra.entity.EventsEntity;
import com.unimitra.entity.EventsRegisterationEntity;

@Repository
public class EventDaoImpl implements EventDao {

	SessionFactory sessionFactory;
	private boolean flase;

	@Override
	public List<EventsEntity> getEventDetails() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from EventsEntity").list();
	}

	@Override
	public EventsEntity getEventDetailById(int eventId) {
		Session session = sessionFactory.getCurrentSession();
		EventsEntity eventDetailsById = session.get(EventsEntity.class, eventId);
		return eventDetailsById;
	}

	@Override
	public String deleteEventById(int eventId) {
		Session session = sessionFactory.getCurrentSession();
		EventsEntity deleteEventById = session.get(EventsEntity.class, eventId);
		if (ObjectUtils.isEmpty(deleteEventById)) {
			return "Event not present";
		}

		deleteEventById.setEventIsActive(flase);
		session.update(deleteEventById);
		return "200";
	}

	@Override
	public EventsEntity postEvent(EventsEntity postEvent) {
		Session session = sessionFactory.getCurrentSession();
		session.save(postEvent);
		return postEvent;
	}

/*	@Override
	public String registerForEvent(int eventId, int userId, boolean eventRegistrationFlag) {
		Session session = sessionFactory.getCurrentSession();
		EventsRegisterationEntity eventRegistrationByUserid = session.createQuery("select e.eventId, u.userId from UserDetailsEntity u"
				+ "RIGHT JION e.EventsEntity");
		return null;
	}
	*/
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	

}
