package com.unimitra.model;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscussionModel {
	private int questionId;
	private String category;
	private String question;
	private String userName;
	private int userId;
	private Date timestamp;
	private List<AnswerModel> answer;
	private boolean discussionThreadActive;
	private int groupId;

}
