package com.unimitra.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "answers")
@JsonInclude(Include.NON_NULL)
public class AnswersEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id")
	private int answerId;

	@Column(name = "answer_posted_by_user_id")
	private int answerPostedByUserId;

	@Column(name = "answer_description")
	private String answerDescription;

	@Column(name = "question_id")
	private int questionId;

	@Column(name = "answer_date_time")
	private Timestamp answerDateTime;

	@Column(name = "answer_is_active")
	private boolean answerIsActive;

	@Column(name = "answer_status")
	private String answerStatus;

}
