package com.unimitra.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.unimitra.dao.EventDao;
import com.unimitra.entity.EventsEntity;


@Repository
public class EventDaoImpl implements EventDao{
	
		
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EventsEntity> getEventDetails() {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		
		List<EventsEntity> eventList = session.createQuery("from EventsEntity").list();
		return eventList;
	}
	
	
	@Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
