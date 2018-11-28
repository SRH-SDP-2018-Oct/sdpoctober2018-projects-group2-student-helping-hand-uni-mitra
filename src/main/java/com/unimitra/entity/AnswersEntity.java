package com.unimitra.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswersEntity {
	
	private int userId;
	private String firstName;
	private String lastName;
	private int enrollmentId;
	private Date dob;
	private String emailId;
	private String userRegistration;
	private String userType;
	private int departmentId;
	private int rewardsCollected;
	private boolean isActive; 
	private Date creationDateTime;
	private Date modifiedDateTime;
	
	
}
