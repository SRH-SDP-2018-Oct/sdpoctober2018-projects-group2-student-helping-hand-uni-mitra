package com.unimitra.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.unimitra.entity.AnswersEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class DiscussionModel {
	private Integer questionId;
	private String category;
	private String question;
	private String userName;
	private Integer userId;
	private Date timestamp;
	private List<AnswersEntity> answer;
	private boolean discussionThreadActive;
	private Integer groupId;
	private Integer categoryId;

}
