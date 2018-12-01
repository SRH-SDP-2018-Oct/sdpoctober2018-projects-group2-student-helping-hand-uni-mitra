package com.unimitra.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerModel {
	int answerId;
	String answer;
	String userName;
	int userId;
	Date time;
	String designation;
	
}
