package com.unimitra.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "questions")
public class QuestionsEntity {
	
	@Column(name = "question_id")
	private int questionId;
	
	@Column(name = "question_category_id")
	private String questionCategoryId;
	
	@Column(name = "question_description")
	private String questionDescription;
	
	@Column(name = "question_posted_by_user_id")
	private int questionPostedByUserId;
	
	@Column(name = "question_creation_date_time")
	private Date questionCreationDateTime;
	
	@Column(name = "question_is_active")
	private String questionIsActive;
	
		


}
