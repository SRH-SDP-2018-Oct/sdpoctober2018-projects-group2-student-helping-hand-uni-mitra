package com.unimitra.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "events")
public class EventsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private int eventId;

	@Column(name = "event_name")
	private String eventName;

	@Column(name = "event_description")
	private String eventDescription;

	@Column(name = "event_charges")
	private String eventCharges;

	@Column(name = "event_location")
	private String eventLocation;

	@Column(name = "event_creation_time")
	private Timestamp eventCreationDate;

	@Column(name = "event_modification_time")
	private Timestamp eventModificationDate;

	@Column(name = "event_created_by_user_id")
	private int eventCreatedbyUserId;

	@Column(name = "event_date_time")
	private Timestamp eventDateTime;

	@Column(name = "event_is_active")
	private boolean eventIsActive;

}
