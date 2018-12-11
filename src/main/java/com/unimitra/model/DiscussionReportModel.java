package com.unimitra.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)

public class DiscussionReportModel {
	private String question;
	private String questionPostedByUser;
	private Date questionPostedOn;
	private String answer="";
	private String answerPostedByUser="";
	private Date answerPostedOn;
	private String answerRemarks="";
	public DiscussionReportModel(String question, String questionPostedByUser, Date questionPostedOn, String answer,
			String answerPostedByUser, Date answerPostedOn, String answerRemarks) {
		this.question = question;
		this.questionPostedByUser = questionPostedByUser;
		this.questionPostedOn = questionPostedOn;
		this.answer = answer;
		this.answerPostedByUser = answerPostedByUser;
		this.answerPostedOn = answerPostedOn;
		this.answerRemarks = answerRemarks;
	}

	
}
