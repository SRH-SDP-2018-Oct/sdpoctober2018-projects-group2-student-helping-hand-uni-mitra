package com.unimitra.service.impl;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.unimitra.dao.CategoryDao;
import com.unimitra.dao.DiscussionDao;
import com.unimitra.entity.AnswersEntity;
import com.unimitra.entity.QuestionsEntity;
import com.unimitra.exception.ErrorCodes;
import com.unimitra.exception.UnimitraException;
import com.unimitra.model.AnswerModel;
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
		questionEntity.setQuestionDescription(discussionModel.getQuestion().trim());
		questionEntity.setQuestionActive(true);
		questionEntity.setQuestionPostedByUserId(discussionModel.getUserId());
		discussionDao.postQuestions(questionEntity);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> answerQuestion(AnswerModel answerModel) {
		AnswersEntity answersEntity = new AnswersEntity();
		answersEntity.setAnswerDescription(answerModel.getAnswer().trim());
		answersEntity.setAnswerIsActive(true);
		answersEntity.setQuestionId(answerModel.getQuestionId());
		answersEntity.setAnswerPostedByUserId(answerModel.getUserId());
		answersEntity.setAnswerDateTime(new Timestamp(System.currentTimeMillis()));
		answersEntity.setAnswerPostedByUserId(answerModel.getUserId());
		discussionDao.postAnswers(answersEntity);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> delete(Integer questionId, Integer answerId, Integer userId)
			throws UnimitraException {
		validateDeleteDiscussionRequest(questionId, answerId);
		if (!(ObjectUtils.isEmpty(questionId))) {
			deleteQuestionIfAddedByUser(questionId, userId);
		} else {
			deleteAnswerIfAddedByUser(answerId, userId);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	private void deleteAnswerIfAddedByUser(Integer answerId, Integer userId) throws UnimitraException {
		if (discussionDao.getAnswerPosterUserId(answerId) == userId) {
			discussionDao.deleteAnswer(answerId);
		} else {
			throw new UnimitraException(ErrorCodes.USER_HAS_NO_ACCESS);
		}
	}

	private void deleteQuestionIfAddedByUser(Integer questionId, Integer userId) throws UnimitraException {
		if (discussionDao.getQuestionPosterUserId(questionId) == userId) {
			discussionDao.deleteQuestion(questionId);
		} else {
			throw new UnimitraException(ErrorCodes.USER_HAS_NO_ACCESS);
		}
	}

	private void validateDeleteDiscussionRequest(Integer questionId, Integer answerId) throws UnimitraException {
		if ((ObjectUtils.isEmpty(questionId)) && (ObjectUtils.isEmpty(answerId))) {
			throw new UnimitraException(ErrorCodes.INVALID_DELETE_DISCUSSION_REQUEST);
		}
		if (!(ObjectUtils.isEmpty(questionId)) && !(ObjectUtils.isEmpty(answerId))) {
			throw new UnimitraException(ErrorCodes.INVALID_DELETE_DISCUSSION_REQUEST);
		}
	}

}
