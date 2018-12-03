package com.unimitra.entity;

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
@Table(name = "event_registration")

public class EventsRegisterationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_registration_id")
	private int eventRegistrationId;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "event_id")
	private int eventId;

	@Column(name = "event_registration_flag")
	private boolean eventRegistrationFlag;

}
