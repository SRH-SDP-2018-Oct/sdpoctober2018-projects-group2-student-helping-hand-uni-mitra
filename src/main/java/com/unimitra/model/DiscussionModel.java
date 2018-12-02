package com.unimitra.model;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscussionModel {
	private int questionId;
	private String question;
	private String userName;
	private String userId;
	private String questionDescription;
	private Date timestamp;
	private List<AnswerModel> answer;
	private String status;

}
