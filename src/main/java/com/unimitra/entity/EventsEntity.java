package com.unimitra.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventsEntity {

	private int eventId;
	private String eventName;
	private String eventDescription;
	private Date eventCreationDate;
	private Date eventModificationDate;
	private String eventCategory;
	private int eventCharges;
	private String eventLocation;
	private Date eventDateTime;
	
	
}
