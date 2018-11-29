package com.unimitra.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.unimitra.dao.EventDao;
import com.unimitra.entity.EventsEntity;

@Repository
public class EventDaoImpl implements EventDao {

	SessionFactory sessionFactory;

	@Override
	public List<EventsEntity> getEventDetails() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from EventsEntity").list();
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
