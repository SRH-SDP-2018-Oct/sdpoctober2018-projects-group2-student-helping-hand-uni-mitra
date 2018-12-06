package com.unimitra.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimitra.dao.EventDao;
import com.unimitra.dao.EventsRegistrationDao;
import com.unimitra.dao.UserDetailsDao;
import com.unimitra.entity.EventsEntity;
import com.unimitra.entity.EventsRegisterationEntity;
import com.unimitra.entity.UserDetailsEntity;
import com.unimitra.service.EventsService;

@Service
@Transactional
public class EventsServiceImpl implements EventsService {

	@Autowired
	EventDao eventsDao;

	@Autowired
	UserDetailsDao userDetailsDao;

	@Autowired
	EventsRegistrationDao eventsRegistrationDao;

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

	@Override
	public String registrationForEvent(EventsRegisterationEntity registerForEvent) {

		UserDetailsEntity user = userDetailsDao.getUserDetails(registerForEvent.getUserId());
		EventsEntity event = eventsDao.getEventDetailById(registerForEvent.getEventId());

		List<EventsRegisterationEntity> eventsRegistrationEntity1 = eventsRegistrationDao
				.getUserByUserIdEventId(user.getUserId(), event.getEventId());

		if (eventsRegistrationEntity1 != null && !eventsRegistrationEntity1.isEmpty()) {
			eventsRegistrationEntity1.get(0).setEventRegistrationFlag(registerForEvent.isEventRegistrationFlag());
			eventsRegistrationDao.updateRegistrationFlag(eventsRegistrationEntity1.get(0));
		} else {
			EventsRegisterationEntity eventsRegisterationEntity = new EventsRegisterationEntity();
			eventsRegisterationEntity.setUserId(user.getUserId());
			eventsRegisterationEntity.setEventId(event.getEventId());
			eventsRegisterationEntity.setEventRegistrationFlag(registerForEvent.isEventRegistrationFlag());
			eventsRegistrationDao.updateRegistrationFlag(eventsRegisterationEntity);
		}

		return "200";
	}

}
