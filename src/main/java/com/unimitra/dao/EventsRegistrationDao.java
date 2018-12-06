package com.unimitra.dao;

import java.util.List;

import com.unimitra.entity.EventsRegisterationEntity;

public interface EventsRegistrationDao {

	List<EventsRegisterationEntity> getUserByUserIdEventId(int userId, int eventId);
	EventsRegisterationEntity updateRegistrationFlag(EventsRegisterationEntity registerForEvent);
	void updateExsistingRegistrationFlag(EventsRegisterationEntity eventsRegistrationEntity1);

}
