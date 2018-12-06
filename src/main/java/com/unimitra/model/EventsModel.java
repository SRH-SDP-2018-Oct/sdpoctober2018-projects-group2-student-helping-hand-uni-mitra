package com.unimitra.model;

import java.sql.Time;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventsModel {
	private int eventid;
	private String eventName;
	private String eventDescription;
	private String eventCategory;
	private String eventLocation;
	private String eventCharges;
	private String eventCreatedBy;
	private Time eventDateTime;
	
}
