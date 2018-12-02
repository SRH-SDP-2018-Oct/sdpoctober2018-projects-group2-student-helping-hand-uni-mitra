package com.unimitra.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.unimitra.dao.EventDao;
import com.unimitra.entity.EventsEntity;

@Repository
public class EventDaoImpl implements EventDao {

	SessionFactory sessionFactory;
	private boolean flase;

	@Override
	public List<EventsEntity> getEventDetails() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from EventsEntity e order by e.eventCreationDate desc").setMaxResults(10).list();
	}

	@Override
	public EventsEntity getEventDetailById(int eventId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(EventsEntity.class, eventId);
		
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

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	

}
