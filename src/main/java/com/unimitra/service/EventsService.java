package com.unimitra.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.unimitra.entity.EventsEntity;
import com.unimitra.entity.EventsRegisterationEntity;
import com.unimitra.exception.UnimitraException;

@Service
public interface EventsService {

	List<EventsEntity> getEventDetails() throws UnimitraException;

	EventsEntity getEventDetailById(int eventId) throws UnimitraException;

	ResponseEntity<String> deleteEventById(int eventId, int createdByuserId) throws UnimitraException;

	EventsEntity postEvent(EventsEntity postEvent);

	ResponseEntity<String> registrationForEvent(EventsRegisterationEntity registerForEvent) throws UnimitraException;

	EventsEntity editEvent(EventsEntity editEvent, int userId) throws UnimitraException;

}
