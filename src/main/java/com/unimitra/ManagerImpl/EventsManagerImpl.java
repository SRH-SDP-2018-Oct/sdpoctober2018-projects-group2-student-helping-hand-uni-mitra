package com.unimitra.ManagerImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimitra.dao.EventDao;
import com.unimitra.entity.EventsEntity;
import com.unimitra.manager.EventsManager;


@Service
@Transactional
public class EventsManagerImpl implements EventsManager{
	
	@Autowired
	EventDao eventsDao;

	@Override
	public List<EventsEntity> getEventDetails() {
		// TODO Auto-generated method stub
		return eventsDao.getEventDetails();
	}

}
