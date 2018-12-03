package com.unimitra.service.impl;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.unimitra.dao.CategoryDao;
import com.unimitra.dao.DiscussionDao;
import com.unimitra.entity.QuestionsEntity;
import com.unimitra.model.DiscussionModel;
import com.unimitra.service.DiscussionService;

@Service
@Transactional
public class DiscussionServiceImpl implements DiscussionService {
	@Autowired
	DiscussionDao discussionDao;
	@Autowired
	CategoryDao categoryDao;

	@Override
	public ResponseEntity<String> postQuestion(DiscussionModel discussionModel) {
		QuestionsEntity questionEntity = new QuestionsEntity();
		questionEntity.setQuestionCategoryId(categoryDao.getCategoryIdFromCategoryName(discussionModel.getCategory()));
		questionEntity.setQuestionCreationDateTime(new Timestamp(System.currentTimeMillis()));
		questionEntity.setQuestionDescription(discussionModel.getQuestion());
		questionEntity.setQuestionActive(true);
		questionEntity.setQuestionPostedByUserId(discussionModel.getUserId());

		String response = discussionDao.postQuestions(questionEntity);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
