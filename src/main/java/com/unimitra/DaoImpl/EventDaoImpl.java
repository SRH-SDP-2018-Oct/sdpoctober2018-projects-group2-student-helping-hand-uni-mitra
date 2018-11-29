package com.unimitra.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.unimitra.dao.EventDao;
import com.unimitra.entity.EventsEntity;


@Repository
public class EventDaoImpl implements EventDao{
	
		
	SessionFactory sessionFactory;
	Session session;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EventsEntity> getEventDetails() {
		// TODO Auto-generated method stub
		session=sessionFactory.getCurrentSession();
		
		List<EventsEntity> eventList = session.createQuery("from EventsEntity orderby eventCreationDate").list();
		return eventList;
	}

}
