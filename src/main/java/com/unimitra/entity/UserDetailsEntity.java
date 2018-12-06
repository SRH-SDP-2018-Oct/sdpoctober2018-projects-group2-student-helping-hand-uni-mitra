package com.unimitra.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user_details")
public class UserDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int userId;
	
	/*@OneToMany(mappedBy ="userDetailsEntity")
	@JoinColumn(name = "answer_posted_by_user_id", referencedColumnName="userId")*/
	@OneToMany(fetch=FetchType.LAZY, targetEntity=AnswersEntity.class, cascade=CascadeType.ALL)
	@JoinColumn(name = "userId", referencedColumnName="user_id")
	private Collection<AnswersEntity> userAns = new ArrayList<AnswersEntity>();
	
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
