package com.unimitra.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnswerModel {
	int answerId;
	String answer;
	String userName;
	int userId;
	Timestamp time;
	String designation;
	int questionId;
}
