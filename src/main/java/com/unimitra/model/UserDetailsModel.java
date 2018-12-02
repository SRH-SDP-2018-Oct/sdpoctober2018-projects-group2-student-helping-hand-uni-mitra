package com.unimitra.model;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsModel {
	private int userId;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String enrollmentId;
	private List<AnswerModel> answerList;
	private List<DiscussionModel> questionList;
	private List<GroupModel> groupList;
		

}
