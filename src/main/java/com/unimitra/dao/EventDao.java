package com.unimitra.dao;

import java.util.List;

import com.unimitra.entity.EventsEntity;
import com.unimitra.entity.EventsRegisterationEntity;

public interface EventDao {

	List<EventsEntity> getEventDetails();

	EventsEntity getEventDetailById(int eventId);

	String deleteEventById(int eventId);
	
	EventsEntity postEvent(EventsEntity postEvent);

	EventsRegisterationEntity getUserEventMapping(int userId, int eventId);

	
	
}
