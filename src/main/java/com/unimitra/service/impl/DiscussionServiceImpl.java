package com.unimitra.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.unimitra.utility.UnimitraConstants;

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

	private static final Logger LOGGER = LogManager.getLogger();

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
		LOGGER.info(UnimitraConstants.POST_QUESTION + UnimitraConstants.COMPLETED + "{}", discussionModel.toString());
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
		LOGGER.info(UnimitraConstants.ANSWER_QUESTION + UnimitraConstants.COMPLETED + "{}", answerModel.toString());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> delete(Integer questionId, Integer answerId, Integer userId, Integer groupId)
			throws UnimitraException {
		validateDeleteDiscussionRequest(questionId, answerId);
		if (!ObjectUtils.isEmpty(groupId)) {
			checkIfUserHasAccessToGroup(userId, groupId);
		}
		if (!(ObjectUtils.isEmpty(questionId))) {
			deleteQuestion(questionId, userId);
		} else {
			deleteAnswer(answerId, userId);
		}
		LOGGER.info(UnimitraConstants.DELETE_DISCUSSION + UnimitraConstants.COMPLETED
				+ "questionId {}, answerId {}, userId {}, goupId {}", questionId, answerId, userId, groupId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> closeDiscussionThread(DiscussionModel discussionModel) throws UnimitraException {
		int userId = discussionModel.getUserId();
		boolean isDiscussionThreadActive = discussionModel.isDiscussionThreadActive();
		int questionId = discussionModel.getQuestionId();
		if (!isUserStaff(userId)) {
			throw new UnimitraException(ErrorCodes.USER_HAS_NO_ACCESS);
		}
		discussionDao.closeQuestionThread(questionId, isDiscussionThreadActive);
		LOGGER.info(UnimitraConstants.CLOSE_THREAD + UnimitraConstants.COMPLETED + "{}", discussionModel.toString());
		return new ResponseEntity<>(HttpStatus.OK);
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
		LOGGER.info(
				UnimitraConstants.SEARCH_DISCUSSION + UnimitraConstants.COMPLETED
						+ "searchString {}, category {}, goupId {}, userId {}",
				searchString, category, groupName, userId);
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

	private void deleteAnswer(Integer answerId, Integer userId) throws UnimitraException {
		if (discussionDao.getAnswerPosterUserId(answerId) == userId || isUserStaff(userId)) {
			discussionDao.deleteAnswer(answerId);
		} else {
			LOGGER.info(UnimitraConstants.UNI_MITRA_ERROR + ErrorCodes.USER_HAS_NO_ACCESS + "answerId= {}, userId= {}",
					answerId, userId);
			throw new UnimitraException(ErrorCodes.USER_HAS_NO_ACCESS);
		}
	}

	private void deleteQuestion(Integer questionId, Integer userId) throws UnimitraException {
		if (discussionDao.getQuestionEntityFromQuestionId(questionId).getQuestionPostedByUserId() == userId
				|| isUserStaff(userId)) {
			discussionDao.deleteQuestion(questionId);
			discussionDao.deletAllAnswersOfQuestion(questionId);
		} else {
			LOGGER.info(
					UnimitraConstants.UNI_MITRA_ERROR + ErrorCodes.USER_HAS_NO_ACCESS + "questionId= {}, userId= {}",
					questionId, userId);
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
			LOGGER.info(UnimitraConstants.UNI_MITRA_ERROR + ErrorCodes.QUESTION_THREAD_INACTIVE + "questionId= {}",
					answerModel.getQuestionId());
			throw new UnimitraException(ErrorCodes.QUESTION_THREAD_INACTIVE);
		}
		if (!discussionDao.getQuestionEntityFromQuestionId(answerModel.getQuestionId()).isQuestionActive()) {
			LOGGER.info(UnimitraConstants.UNI_MITRA_ERROR + ErrorCodes.QUESTION_NOT_PRESENT + "questionId= {}",
					answerModel.getQuestionId());
			throw new UnimitraException(ErrorCodes.QUESTION_NOT_PRESENT);
		}
		QuestionsEntity questionEntity = discussionDao.getQuestionEntityFromQuestionId(answerModel.getQuestionId());
		if (!ObjectUtils.isEmpty(questionEntity.getQuestionGroupId())) {
			checkIfUserHasAccessToGroup(answerModel.getUserId(), questionEntity.getQuestionGroupId());
		}
	}

	private void checkIfUserHasAccessToGroup(int userId, int questionGroupId) throws UnimitraException {
		if (!groupDao.checkIfUserHasAccessToGroup(userId, questionGroupId)) {
			LOGGER.info(UnimitraConstants.UNI_MITRA_ERROR + ErrorCodes.USER_DOES_NOT_HAVE_ACCESS_TO_GROUP
					+ "userId= {}, groupId= {}", userId, questionGroupId);
			throw new UnimitraException(ErrorCodes.USER_DOES_NOT_HAVE_ACCESS_TO_GROUP);
		}
	}

}
