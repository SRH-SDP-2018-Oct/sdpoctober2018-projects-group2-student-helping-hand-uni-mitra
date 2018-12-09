package com.unimitra.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.unimitra.dao.CategoryDao;
import com.unimitra.dao.DiscussionDao;
import com.unimitra.dao.GroupDao;
import com.unimitra.dao.UserDetailsDao;
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
	@Autowired
	UserDetailsDao userDetailsDao;
	@Autowired
	GroupDao groupDao;

	@Override
	public ResponseEntity<String> postQuestion(DiscussionModel discussionModel) throws UnimitraException {
		QuestionsEntity questionEntity = new QuestionsEntity();
		questionEntity.setQuestionCategoryId(categoryDao.getCategoryIdFromCategoryName(discussionModel.getCategory()));
		questionEntity.setQuestionCreationDateTime(new Timestamp(System.currentTimeMillis()));
		questionEntity.setQuestionDescription(discussionModel.getQuestion().trim());
		questionEntity.setQuestionActive(true);
		questionEntity.setDiscussionThreadActive(true);
		questionEntity.setQuestionPostedByUserId(discussionModel.getUserId());
		Integer questionGroupId = discussionModel.getGroupId();
		if (!ObjectUtils.isEmpty(questionGroupId)) {
			checkIfUserHasAccessToGroup(discussionModel.getUserId(), questionGroupId);
			questionEntity.setQuestionGroupId(questionGroupId);
		}

		discussionDao.postQuestions(questionEntity);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> answerQuestion(AnswerModel answerModel) throws UnimitraException {
		AnswersEntity answersEntity = new AnswersEntity();
		answersEntity.setAnswerDescription(answerModel.getAnswer().trim());
		answersEntity.setAnswerIsActive(true);
		answersEntity.setQuestionId(answerModel.getQuestionId());
		answersEntity.setAnswerPostedByUserId(answerModel.getUserId());
		answersEntity.setAnswerDateTime(new Timestamp(System.currentTimeMillis()));
		answersEntity.setAnswerPostedByUserId(answerModel.getUserId());
		checkIfPostAnswerIsPossible(answerModel);
		discussionDao.postAnswers(answersEntity);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> delete(Integer questionId, Integer answerId, Integer userId, Integer groupId)
			throws UnimitraException {
		validateDeleteDiscussionRequest(questionId, answerId);
		if (ObjectUtils.isEmpty(groupId)) {
			checkIfUserHasAccessToGroup(userId, groupId);
		}
		if (!(ObjectUtils.isEmpty(questionId))) {
			deleteQuestionIfAddedByUser(questionId, userId);
		} else {
			deleteAnswerIfAddedByUser(answerId, userId);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<String> closeDiscussionThread(DiscussionModel discussionModel) throws UnimitraException {
		int userId = discussionModel.getUserId();
		boolean isDiscussionThreadActive = discussionModel.isDiscussionThreadActive();
		int questionId = discussionModel.getQuestionId();
		if (isUserStaff(userId)) {
			discussionDao.closeQuestionThread(questionId, isDiscussionThreadActive);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<List<DiscussionModel>> getDiscussions(String searchString, String category, String groupName,
			Integer userId) throws UnimitraException {
		ResponseEntity<List<DiscussionModel>> response = null;
		if (StringUtils.isNotEmpty(searchString) && StringUtils.isEmpty(category) && StringUtils.isEmpty(groupName)
				&& ObjectUtils.isEmpty(userId)) {
			response = searchOnKeyword(searchString);
		} else if (StringUtils.isEmpty(searchString) && StringUtils.isNotEmpty(category)
				&& StringUtils.isEmpty(groupName) && ObjectUtils.isEmpty(userId)) {
			response = searchOnCategory(category);
		} else if (StringUtils.isNotEmpty(searchString) && StringUtils.isEmpty(category)
				&& StringUtils.isNotEmpty(groupName) && !ObjectUtils.isEmpty(userId)) {
			response = searchOnKeywordInGroup(searchString, groupName, userId);
		} else if (StringUtils.isEmpty(searchString) && StringUtils.isNotEmpty(category)
				&& StringUtils.isNotEmpty(groupName) && !ObjectUtils.isEmpty(userId)) {
			response = searchOnCategoryInGroup(category, groupName, userId);
		} else {
			throw new UnimitraException(ErrorCodes.INVALID_SEARCH_REQUEST);
		}
		return response;
	}

	private ResponseEntity<List<DiscussionModel>> searchOnCategoryInGroup(String category, String groupName, int userId)
			throws UnimitraException {
		int categoryid = categoryDao.getCategoryIdFromCategoryName(category.trim());
		int groupId = groupDao.getGroupIdFromGroupName(groupName);
		checkIfUserHasAccessToGroup(userId, groupId);
		List<DiscussionModel> listOfDiscussionModel = discussionDao.searchOnCategoryInGroup(categoryid, groupId);
		checkIfCollectionIsEmpty(listOfDiscussionModel);
		return new ResponseEntity<>(listOfDiscussionModel, HttpStatus.OK);
	}

	private ResponseEntity<List<DiscussionModel>> searchOnKeywordInGroup(String searchString, String groupName,
			int userId) throws UnimitraException {
		int groupId = groupDao.getGroupIdFromGroupName(groupName);
		checkIfUserHasAccessToGroup(userId, groupId);
		List<DiscussionModel> listOfDiscussionModel = discussionDao.searchOnKeywordInGroup(searchString, groupId,
				userId);
		checkIfCollectionIsEmpty(listOfDiscussionModel);
		return new ResponseEntity<>(listOfDiscussionModel, HttpStatus.OK);

	}

	private ResponseEntity<List<DiscussionModel>> searchOnCategory(String category) throws UnimitraException {
		int categoryid = categoryDao.getCategoryIdFromCategoryName(category.trim());
		List<DiscussionModel> listOfDiscussionModel = discussionDao.searchOnCategory(categoryid);
		checkIfCollectionIsEmpty(listOfDiscussionModel);
		return new ResponseEntity<>(listOfDiscussionModel, HttpStatus.OK);
	}

	private ResponseEntity<List<DiscussionModel>> searchOnKeyword(String searchString) throws UnimitraException {
		List<DiscussionModel> listOfDiscussionModel = discussionDao.searchOnKeyword(searchString);
		checkIfCollectionIsEmpty(listOfDiscussionModel);
		return new ResponseEntity<>(listOfDiscussionModel, HttpStatus.OK);

	}

	private void checkIfCollectionIsEmpty(List<DiscussionModel> model) throws UnimitraException {
		if (CollectionUtils.isEmpty(model)) {
			throw new UnimitraException(ErrorCodes.NO_RESULTS_FOUND);
		}
	}

	private boolean isUserStaff(int userId) throws UnimitraException {
		return userDetailsDao.getUserDetails(userId).getUserType().equals("Staff");
	}

	private void deleteAnswerIfAddedByUser(Integer answerId, Integer userId) throws UnimitraException {
		if (discussionDao.getAnswerPosterUserId(answerId) == userId || isUserStaff(userId)) {
			discussionDao.deleteAnswer(answerId);
		} else {
			throw new UnimitraException(ErrorCodes.USER_HAS_NO_ACCESS);
		}
	}

	private void deleteQuestionIfAddedByUser(Integer questionId, Integer userId) throws UnimitraException {
		if (discussionDao.getQuestionEntityFromQuestionId(questionId).getQuestionPostedByUserId() == userId
				|| isUserStaff(userId)) {
			discussionDao.deleteQuestion(questionId);
			discussionDao.deletAllAnswersOfQuestion(questionId);
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

	private void checkIfPostAnswerIsPossible(AnswerModel answerModel) throws UnimitraException {
		if (!discussionDao.getQuestionEntityFromQuestionId(answerModel.getQuestionId()).isDiscussionThreadActive()) {
			throw new UnimitraException(ErrorCodes.QUESTION_THREAD_INACTIVE);
		}
		if (!discussionDao.getQuestionEntityFromQuestionId(answerModel.getQuestionId()).isQuestionActive()) {
			throw new UnimitraException(ErrorCodes.QUESTION_NOT_PRESENT);
		}
		Integer groupId = discussionDao.getQuestionEntityFromQuestionId(answerModel.getQuestionId())
				.getQuestionGroupId();
		if (!ObjectUtils.isEmpty(groupId)) {
			checkIfUserHasAccessToGroup(answerModel.getUserId(), groupId);
			checkIfQuestionBelongsToGroup(answerModel.getQuestionId(), groupId);
		}
	}

	private void checkIfQuestionBelongsToGroup(int questionId, int groupId) throws UnimitraException {
		if (discussionDao.getQuestionEntityFromQuestionId(questionId).getQuestionGroupId() != groupId) {
			throw new UnimitraException(ErrorCodes.QUESTION_NOT_PRESENT);
		}
	}

	private void checkIfUserHasAccessToGroup(int userId, int questionGroupId) throws UnimitraException {
		if (!groupDao.checkIfUserHasAccessToGroup(userId, questionGroupId)) {
			throw new UnimitraException(ErrorCodes.USER_DOES_NOT_HAVE_ACCESS_TO_GROUP);
		}
	}

}
