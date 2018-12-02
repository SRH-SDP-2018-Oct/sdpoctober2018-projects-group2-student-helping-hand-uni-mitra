package com.unimitra.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unimitra.entity.EventsEntity;

@Service
public interface EventsService {

	List<EventsEntity> getEventDetails();

	EventsEntity getEventDetailById(int eventId);

	String deleteEventById(int eventId);

	EventsEntity postEvent(EventsEntity postEvent);

	//String registerForEvent(int eventId, int userId, boolean eventRegistrationFlag);

}
