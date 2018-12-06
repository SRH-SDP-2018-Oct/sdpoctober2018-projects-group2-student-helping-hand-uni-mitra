package com.unimitra.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerModel {
	int answerId;
	String answer;
	String userName;
	int userId;
	Timestamp time;
	String designation;
	int questionId;
}
