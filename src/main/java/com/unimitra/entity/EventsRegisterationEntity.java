package com.unimitra.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventsRegisterationEntity {

	private int eventRegistrationId;
	private int userId;
	private int eventId;
	private boolean eventStatusFlag;
	
}
