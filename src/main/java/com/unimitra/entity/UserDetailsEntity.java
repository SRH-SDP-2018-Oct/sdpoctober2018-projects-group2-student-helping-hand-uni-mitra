package com.unimitra.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "user_details")
public class UserDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "enrollment_id")
	private String enrollmentId;
	
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "user_designation")
	private String userDesignation;
	
	@Column(name = "user_type")
	private String userType;
	
	@Column(name = "department_id")
	private int departmentId;
	
	@Column(name = "rewards")
	private String rewards;
	
	@Column(name = "user_creation_date_time")
	private Date userCreationDateTime;
	
	@Column(name = "user_modification_date_time")
	private Date userModificationDateTime;
	
	@Column(name = "user_is_active")
	private boolean userIsActive;
	
}
