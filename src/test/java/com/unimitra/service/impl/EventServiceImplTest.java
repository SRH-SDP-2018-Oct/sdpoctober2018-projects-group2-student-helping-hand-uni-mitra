package com.unimitra.service.impl;

import static org.junit.Assert.fail;

import java.sql.Timestamp;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.unimitra.dao.EventDao;

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
	public static final Timestamp EVENT_DATE_AND_TIME = null;
	public static final boolean EVENT_IS_ACTIVE = true;
	public static final int USER_ID = 2;
	public static final int EVENT_ID = 4;
	public static final boolean EVNTE_REGISTRATION_FLAG=true;
			

	@Ignore
	@Test
	public void testGetEventDetails() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetEventDetailById() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testDeleteEventById() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testPostEvent() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testRegistrationForEvent() {
		fail("Not yet implemented");
	}

}
