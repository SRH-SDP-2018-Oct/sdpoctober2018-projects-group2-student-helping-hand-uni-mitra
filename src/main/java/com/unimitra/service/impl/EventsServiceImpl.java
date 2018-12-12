package com.unimitra.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.unimitra.dao.EventDao;
import com.unimitra.dao.UserDetailsDao;
import com.unimitra.email.EmailUtility;
import com.unimitra.entity.EventsEntity;
import com.unimitra.entity.EventsRegisterationEntity;
import com.unimitra.entity.UserDetailsEntity;
import com.unimitra.exception.ErrorCodes;
import com.unimitra.exception.UnimitraException;
import com.unimitra.service.EventsService;

@Service
@Transactional
public class EventsServiceImpl implements EventsService {

	@Autowired
	EmailUtility emailUtility;

	@Autowired
	EventDao eventsDao;

	@Autowired
	UserDetailsDao userDetailsDao;

	@Override
	public List<EventsEntity> getEventDetails() throws UnimitraException {
		return eventsDao.getEventDetails();
	}

	@Override
	public EventsEntity getEventDetailById(int eventId) throws UnimitraException {
		return eventsDao.getEventDetailById(eventId);
	}

	@Override
	public ResponseEntity<String> deleteEventById(int eventId, int createdByuserId) throws UnimitraException {
		EventsEntity evntByEventId = eventsDao.getEventDetailById(eventId);
		if (createdByuserId != evntByEventId.getEventCreatedbyUserId()) {
			throw new UnimitraException(ErrorCodes.USER_IS_NOT_AUTHORISED);
		}

		eventsDao.deleteEventById(eventId);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Override
	public EventsEntity postEvent(EventsEntity postEvent) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		postEvent.setEventCreationDate(time);
		postEvent.setEventModificationDate(time);
		postEvent = eventsDao.postEvent(postEvent);
		return postEvent;
	}

	@Override
	public ResponseEntity<String> registrationForEvent(EventsRegisterationEntity registerForEvent)
			throws UnimitraException {

		UserDetailsEntity user = userDetailsDao.getUserDetails(registerForEvent.getUserId());
		EventsEntity event = eventsDao.getEventDetailById(registerForEvent.getEventId());

		List<EventsRegisterationEntity> eventsRegistrationEntity1 = eventsDao.getUserByUserIdEventId(user.getUserId(),
				event.getEventId());

		if (eventsRegistrationEntity1 != null && !eventsRegistrationEntity1.isEmpty()) {
			eventsRegistrationEntity1.get(0).setEventRegistrationFlag(registerForEvent.isEventRegistrationFlag());
			eventsDao.updateRegistrationFlag(eventsRegistrationEntity1.get(0));
			if (!(registerForEvent.isEventRegistrationFlag())) {
				emailUtility.sendSimpleMessage(user.getEmailId(), event.getEventName(), event.getEventDescription());
			}

		} else {
			EventsRegisterationEntity eventsRegisterationEntity = new EventsRegisterationEntity();
			eventsRegisterationEntity.setUserId(user.getUserId());
			eventsRegisterationEntity.setEventId(event.getEventId());
			eventsRegisterationEntity.setEventRegistrationFlag(registerForEvent.isEventRegistrationFlag());
			eventsDao.updateRegistrationFlag(eventsRegisterationEntity);
			if (!(registerForEvent.isEventRegistrationFlag())) {
				emailUtility.sendSimpleMessage(user.getEmailId(), event.getEventName(), event.getEventDescription());
			}
		}

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Override
	public EventsEntity editEvent(EventsEntity editEvent, int userId) throws UnimitraException {
		Timestamp time = new Timestamp(System.currentTimeMillis());

		EventsEntity eventById = eventsDao.getEventDetailById(editEvent.getEventId());

		if (eventById.getEventCreatedbyUserId() != userId) {
			throw new UnimitraException(ErrorCodes.USER_IS_NOT_AUTHORISED);
		}

		if (!ObjectUtils.isEmpty(editEvent.getEventCharges())) {
			eventById.setEventCharges(editEvent.getEventCharges());
		}
		if (!ObjectUtils.isEmpty(editEvent.getEventDescription())) {
			eventById.setEventDescription(editEvent.getEventDescription());
		}
		if (!ObjectUtils.isEmpty(editEvent.getEventName())) {
			eventById.setEventName(editEvent.getEventName());
		}
		if (!ObjectUtils.isEmpty(editEvent.getEventDateTime())) {
			eventById.setEventDateTime(editEvent.getEventDateTime());
		}
		if (!ObjectUtils.isEmpty(editEvent.isEventIsActive())) {
			eventById.setEventIsActive(editEvent.isEventIsActive());
		}
		if (!ObjectUtils.isEmpty(editEvent.getEventLocation())) {
			eventById.setEventLocation(editEvent.getEventLocation());
		}
		eventById.setEventModificationDate(time);

		eventsDao.updateEvent(eventById);
		return eventById;

	}

}
