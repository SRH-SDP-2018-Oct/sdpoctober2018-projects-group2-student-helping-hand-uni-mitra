package com.unimitra.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionsEntity {
	
	private int questionId;
	private String questionPostedByUserId;
	private String questionDescription;
	private String questionCategory;
	private Date questionCreationDateTime;
	private String questionStatus;
	private int categoryId;		


}
