package com.unimitra.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class UserDetailsModel {
	private int userId;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String enrollmentId;
	private String emailId;
	private String phoneNumber;
	private String userDesignation;
	private String userType;
	private List<?> answerList;
	private List<?> questionList;
	private List<?> groupList;
	private List<?> eventsList;

}
