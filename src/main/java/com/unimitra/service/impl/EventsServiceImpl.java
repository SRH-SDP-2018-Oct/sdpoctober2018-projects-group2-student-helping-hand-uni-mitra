package com.unimitra.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimitra.dao.EventDao;
import com.unimitra.entity.EventsEntity;
import com.unimitra.service.EventsService;

@Service
@Transactional
public class EventsServiceImpl implements EventsService {

	@Autowired
	EventDao eventsDao;

	@Override
	public List<EventsEntity> getEventDetails() {		
		return eventsDao.getEventDetails();
	}

	@Override
	public EventsEntity getEventDetailById(int eventId) {
		return eventsDao.getEventDetailById(eventId);
	}

	@Override
	public String deleteEventById(int eventId) {
		return eventsDao.deleteEventById(eventId);

		

	}

	@Override
	public EventsEntity postEvent(EventsEntity postEvent) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		postEvent.setEventCreationDate(time);
		postEvent.setEventCreationDate(time);
		postEvent = eventsDao.postEvent(postEvent);
		return postEvent;
	}

}
