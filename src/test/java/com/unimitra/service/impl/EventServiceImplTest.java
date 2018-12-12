package com.unimitra.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.unimitra.dao.EventDao;
import com.unimitra.entity.EventsEntity;
import com.unimitra.exception.UnimitraException;

@RunWith(SpringRunner.class)
public class EventServiceImplTest {

	@InjectMocks
	EventsServiceImpl eventsServiceImpl;

	@Mock
	EventDao eventDao;

	public static final String EVENT_NAME = "Resume Builder";
	public static final String EVENT_DESCRIPTION = "How to build a german resume";
	public static final String EVENT_CHERGES = "0Euros";
	public static final String EVENT_LOCATION = "SRH Campus";
	public static final int EVENT_CREATED_BY_USERID = 5;
	public static final Timestamp TIME = new Timestamp(System.currentTimeMillis());
	public static final boolean EVENT_IS_ACTIVE = true;
	public static final int USER_ID = 2;
	public static final int EVENT_ID = 1;
	public static final boolean EVNTE_REGISTRATION_FLAG = true;

	@Test
	public void testPostEvent() throws UnimitraException {
		EventsEntity eventEntity = createValidEventToPost();
		EventsEntity expectedEventEntity = getExpectedEventsEntity();
		EventsEntity mockEventEntityFromDao = getMockEventEntityFromDAO();
		when(eventDao.postEvent(eventEntity)).thenReturn(mockEventEntityFromDao);
		EventsEntity actualEventEntity = eventsServiceImpl.postEvent(eventEntity);
		assertEquals(expectedEventEntity.getEventDescription(), actualEventEntity.getEventDescription());
	}

	private EventsEntity getMockEventEntityFromDAO() {
		EventsEntity mockEventsEntity = new EventsEntity();
		mockEventsEntity.setEventId(1);
		mockEventsEntity.setEventCharges(EVENT_CHERGES);
		mockEventsEntity.setEventCreatedbyUserId(EVENT_CREATED_BY_USERID);
		mockEventsEntity.setEventDescription(EVENT_DESCRIPTION);
		mockEventsEntity.setEventLocation(EVENT_LOCATION);
		mockEventsEntity.setEventName(EVENT_NAME);
		mockEventsEntity.setEventDateTime(TIME);
		mockEventsEntity.setEventIsActive(EVENT_IS_ACTIVE);
		mockEventsEntity.setEventCreationDate(TIME);
		mockEventsEntity.setEventModificationDate(TIME);
		return mockEventsEntity;
	}

	private EventsEntity getExpectedEventsEntity() {
		EventsEntity expectedEventEntity = new EventsEntity();
		expectedEventEntity.setEventId(1);
		expectedEventEntity.setEventCharges(EVENT_CHERGES);
		expectedEventEntity.setEventCreatedbyUserId(EVENT_CREATED_BY_USERID);
		expectedEventEntity.setEventDescription(EVENT_DESCRIPTION);
		expectedEventEntity.setEventLocation(EVENT_LOCATION);
		expectedEventEntity.setEventName(EVENT_NAME);
		expectedEventEntity.setEventDateTime(TIME);
		expectedEventEntity.setEventIsActive(EVENT_IS_ACTIVE);
		expectedEventEntity.setEventCreationDate(TIME);
		expectedEventEntity.setEventModificationDate(TIME);
		return expectedEventEntity;
	}

	private EventsEntity createValidEventToPost() {
		EventsEntity eventEntity = new EventsEntity();
		eventEntity.setEventId(1);
		eventEntity.setEventCharges(EVENT_CHERGES);
		eventEntity.setEventCreatedbyUserId(EVENT_CREATED_BY_USERID);
		eventEntity.setEventDescription(EVENT_DESCRIPTION);
		eventEntity.setEventLocation(EVENT_LOCATION);
		eventEntity.setEventName(EVENT_NAME);
		eventEntity.setEventDateTime(TIME);
		eventEntity.setEventIsActive(EVENT_IS_ACTIVE);
		eventEntity.setEventCreationDate(TIME);
		eventEntity.setEventModificationDate(TIME);
		return eventEntity;
	}

}
