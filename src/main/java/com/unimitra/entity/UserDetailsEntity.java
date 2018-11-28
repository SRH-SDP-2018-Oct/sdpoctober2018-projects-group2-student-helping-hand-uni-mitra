package com.unimitra.entity;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode

public class UserDetailsEntity {
	
	private int userId;
	private String firstName;
	private String lastName;
	private String enrollmentId;
	private Date dateBirth;
	private String emailId;
	private String userDesignation;
	private String userType;
	private String departmentId;
	private String reward;
	private Date creationDateTime;
	private Date modificationDateTime;
	private boolean isActive;
}
