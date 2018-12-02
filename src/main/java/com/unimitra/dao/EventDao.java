package com.unimitra.dao;

import java.util.List;

import com.unimitra.entity.EventsEntity;

public interface EventDao {

	List<EventsEntity> getEventDetails();

	EventsEntity getEventDetailById(int eventId);

	String deleteEventById(int eventId);

	//String registerForEvent(int eventId, int userId, boolean eventRegistrationFlag);

	EventsEntity postEvent(EventsEntity postEvent);
}
