package com.unimitra.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.unimitra.dao.CategoryDao;
import com.unimitra.dao.DiscussionDao;
import com.unimitra.dao.GroupDao;
import com.unimitra.dao.UserDetailsDao;
import com.unimitra.entity.QuestionsEntity;
import com.unimitra.entity.UserDetailsEntity;
import com.unimitra.exception.UnimitraException;
import com.unimitra.model.AnswerModel;
import com.unimitra.model.DiscussionModel;

@RunWith(SpringRunner.class)
public class DiscussionServiceImplTest {

	@InjectMocks
	DiscussionServiceImpl discussionServiceImpl;

	@Mock
	DiscussionDao discussionDao;
	@Mock
	CategoryDao categoryDao;
	@Mock
	UserDetailsDao userDetailsDao;
	@Mock
	GroupDao groupDao;

	public static final String VALID_QUESTION = "Do we have a gym available in Uniersity";
	public static final String VALID_ANSWER = "Yes, near the bibliothek";
	public static final int VALID_ANSWER_ID = 16;
	public static final int VALID_QUESTION_ID = 31;
	public static final int DELETED_QUESTION_ID = 80;
	public static final String VALID_NAME = "Shardul Sonar";
	public static final int VALID_USER_ID_OF_STUDENT_1 = 16;
	public static final int VALID_USER_ID_OF_STUDENT_2 = 21;
	public static final String VALID_CATEGORY = "Others";
	public static final int VALID_CATEGORY_ID = 26;
	public static final int VALID_GROUP_ID_1 = 12;
	public static final int VALID_USER_ID_OF_STAFF = 20;

	public static final String VALID_NAME_OF_STAFF = "Prof. Dr. Ajunkya Prabhune";
	
	// Test Negative Post Question
	// Test post question in group
	// Test post answer 
	// Test post answer in group
	
	@Test
	public void testPostQuestion() throws UnimitraException {
		DiscussionModel discussionModel = createValidDiscussionModelForPostQuestion();
		when(categoryDao.getCategoryIdFromCategoryName(VALID_CATEGORY)).thenReturn(VALID_CATEGORY_ID);
		HttpStatus expectedValue = HttpStatus.CREATED;
		HttpStatus actualValue = discussionServiceImpl.postQuestion(discussionModel).getStatusCode();
		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void testPostQuestionInGroup() throws UnimitraException {
		DiscussionModel discussionModel = createValidDiscussionModelForPostQuestion();
		discussionModel.setGroupId(VALID_GROUP_ID_1);
		when(categoryDao.getCategoryIdFromCategoryName(VALID_CATEGORY)).thenReturn(VALID_CATEGORY_ID);
		when(groupDao.checkIfUserHasAccessToGroup(discussionModel.getUserId(), discussionModel.getGroupId())).thenReturn(true);
		HttpStatus expectedValue = HttpStatus.CREATED;
		HttpStatus actualValue = discussionServiceImpl.postQuestion(discussionModel).getStatusCode();
		assertEquals(expectedValue, actualValue);
	}
	
	@Test(expected = UnimitraException.class)
	public void testPostQuestionInGroupWhereUserIdDoesnotHaveAccessToGroup() throws UnimitraException {
		DiscussionModel discussionModel = createValidDiscussionModelForPostQuestion();
		discussionModel.setGroupId(VALID_GROUP_ID_1);
		when(categoryDao.getCategoryIdFromCategoryName(VALID_CATEGORY)).thenReturn(VALID_CATEGORY_ID);
		when(groupDao.checkIfUserHasAccessToGroup(discussionModel.getUserId(), discussionModel.getGroupId())).thenReturn(false);
		discussionServiceImpl.postQuestion(discussionModel);
	}
	
	@Test
	public void testValidAnswerQuestionWithoutGroup() throws UnimitraException {
		AnswerModel answerModel = createValidAnswerModel();
		HttpStatus expectedValue = HttpStatus.CREATED;
		QuestionsEntity questionEntity = new QuestionsEntity();
		questionEntity.setQuestionActive(true);
		questionEntity.setDiscussionThreadActive(true);
		when(discussionDao.getQuestionEntityFromQuestionId(VALID_QUESTION_ID)).thenReturn(questionEntity);
		HttpStatus actualValue = discussionServiceImpl.answerQuestion(answerModel).getStatusCode();
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testValidAnswerQuestionInGroup() throws UnimitraException {
		AnswerModel answerModel = createValidAnswerModel();
		HttpStatus expectedValue = HttpStatus.CREATED;
		QuestionsEntity questionEntity = new QuestionsEntity();
		questionEntity.setQuestionActive(true);
		questionEntity.setDiscussionThreadActive(true);
		questionEntity.setQuestionGroupId(VALID_GROUP_ID_1);
		when(discussionDao.getQuestionEntityFromQuestionId(VALID_QUESTION_ID)).thenReturn(questionEntity);
		when(groupDao.checkIfUserHasAccessToGroup(answerModel.getUserId(), VALID_GROUP_ID_1)).thenReturn(true);
		HttpStatus actualValue = discussionServiceImpl.answerQuestion(answerModel).getStatusCode();
		assertEquals(expectedValue, actualValue);
	}

	@Test(expected = UnimitraException.class)
	public void testPostAnswerInGroupWhereUserIdDoesnotHaveAccessToGroup() throws UnimitraException {
		AnswerModel answerModel = new AnswerModel();
		answerModel.setAnswer(VALID_ANSWER);
		answerModel.setQuestionId(VALID_QUESTION_ID);
		answerModel.setUserId(VALID_USER_ID_OF_STUDENT_2);
		answerModel.setUserName(VALID_NAME);
		QuestionsEntity questionEntity = new QuestionsEntity();
		questionEntity.setQuestionGroupId(VALID_GROUP_ID_1);
		questionEntity.setQuestionActive(true);
		questionEntity.setDiscussionThreadActive(false);
		when(discussionDao.getQuestionEntityFromQuestionId(answerModel.getQuestionId())).thenReturn(questionEntity);
		when(groupDao.checkIfUserHasAccessToGroup(answerModel.getUserId(), VALID_GROUP_ID_1)).thenReturn(false);
		discussionServiceImpl.answerQuestion(answerModel);
	}
	
	@Test(expected = UnimitraException.class)
	public void testPostAnswerForInactiveQuestionThread() throws UnimitraException {
		AnswerModel answerModel = createValidAnswerModel();
		QuestionsEntity questionEntity = new QuestionsEntity();
		questionEntity.setQuestionActive(true);
		questionEntity.setDiscussionThreadActive(false);
		when(discussionDao.getQuestionEntityFromQuestionId(VALID_QUESTION_ID)).thenReturn(questionEntity);
		discussionServiceImpl.answerQuestion(answerModel);
	}

	@Test(expected = UnimitraException.class)
	public void testPostAnswerForInvalidQuestionId() throws UnimitraException {
		AnswerModel answerModel = new AnswerModel();
		answerModel.setAnswer(VALID_ANSWER);
		answerModel.setQuestionId(DELETED_QUESTION_ID);
		answerModel.setUserId(VALID_USER_ID_OF_STUDENT_1);
		answerModel.setUserName(VALID_NAME);
		QuestionsEntity questionEntity = new QuestionsEntity();
		questionEntity.setQuestionActive(false);
		questionEntity.setDiscussionThreadActive(true);
		when(discussionDao.getQuestionEntityFromQuestionId(DELETED_QUESTION_ID)).thenReturn(questionEntity);
		discussionServiceImpl.answerQuestion(answerModel);
	}
	
	@Test
	public void testDeleteQuestionByQuestionPoster() throws UnimitraException {
		QuestionsEntity questionEntity = getQuestionsEntityForDeleteRequestTesting();
		UserDetailsEntity userEntity = new UserDetailsEntity();
		userEntity.setUserId((Integer)VALID_USER_ID_OF_STUDENT_1);
		userEntity.setUserType("Student");
		when(discussionDao.getQuestionEntityFromQuestionId(VALID_QUESTION_ID)).thenReturn(questionEntity);
		when(userDetailsDao.getUserDetails(VALID_USER_ID_OF_STUDENT_1)).thenReturn(userEntity);
		HttpStatus expectedValue = HttpStatus.OK;
		HttpStatus actualValue = discussionServiceImpl.delete(VALID_QUESTION_ID, null, VALID_USER_ID_OF_STUDENT_1, null).getStatusCode();
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testDeleteQuestionByStaff() throws UnimitraException {
		QuestionsEntity questionEntity = getQuestionsEntityForDeleteRequestTesting();
		UserDetailsEntity userEntity = new UserDetailsEntity();
		userEntity.setUserId((Integer)VALID_USER_ID_OF_STAFF);
		userEntity.setUserType("Staff");
		when(discussionDao.getQuestionEntityFromQuestionId(VALID_QUESTION_ID)).thenReturn(questionEntity);
		when(userDetailsDao.getUserDetails(VALID_USER_ID_OF_STAFF)).thenReturn(userEntity);
		HttpStatus expectedValue = HttpStatus.OK;
		
		HttpStatus actualValue = discussionServiceImpl.delete(VALID_QUESTION_ID, null, VALID_USER_ID_OF_STAFF, null).getStatusCode();
		assertEquals(expectedValue, actualValue);
	}
	
	@Test(expected = UnimitraException.class)
	public void testDeleteQuestionRequestForUnauthorizedUser() throws UnimitraException {
		QuestionsEntity questionEntity = getQuestionsEntityForDeleteRequestTesting();
		UserDetailsEntity userEntity = new UserDetailsEntity();
		userEntity.setUserId((Integer)VALID_USER_ID_OF_STUDENT_2);
		userEntity.setUserType("Student");
		when(discussionDao.getQuestionEntityFromQuestionId(VALID_QUESTION_ID)).thenReturn(questionEntity);
		when(userDetailsDao.getUserDetails(VALID_USER_ID_OF_STUDENT_2)).thenReturn(userEntity);
		discussionServiceImpl.delete(VALID_QUESTION_ID, null, VALID_USER_ID_OF_STUDENT_2, null);
	}
	
	@Test
	public void testDeleteQuestionInGroup() throws UnimitraException {
		QuestionsEntity questionEntity = getQuestionsEntityForDeleteRequestTesting();
		questionEntity.setQuestionGroupId(VALID_GROUP_ID_1);
		UserDetailsEntity userEntity = new UserDetailsEntity();
		userEntity.setUserId((Integer)VALID_USER_ID_OF_STUDENT_1);
		userEntity.setUserType("Student");
		when(discussionDao.getQuestionEntityFromQuestionId(VALID_QUESTION_ID)).thenReturn(questionEntity);
		when(groupDao.checkIfUserHasAccessToGroup(VALID_USER_ID_OF_STUDENT_1, VALID_GROUP_ID_1)).thenReturn(true);
		when(userDetailsDao.getUserDetails(VALID_USER_ID_OF_STUDENT_1)).thenReturn(userEntity);
		HttpStatus expectedValue = HttpStatus.OK;
		HttpStatus actualValue = discussionServiceImpl.delete(VALID_QUESTION_ID, null, VALID_USER_ID_OF_STUDENT_1, VALID_GROUP_ID_1).getStatusCode();
		assertEquals(expectedValue, actualValue);
	}
	
	@Test(expected=UnimitraException.class)
	public void testDeleteQuestionInGroupWithUnAuthorizedUserAccessToGroup() throws UnimitraException {
		QuestionsEntity questionEntity = getQuestionsEntityForDeleteRequestTesting();
		questionEntity.setQuestionGroupId(VALID_GROUP_ID_1);
		UserDetailsEntity userEntity = new UserDetailsEntity();
		userEntity.setUserId((Integer)VALID_USER_ID_OF_STUDENT_1);
		userEntity.setUserType("Student");
		when(discussionDao.getQuestionEntityFromQuestionId(VALID_QUESTION_ID)).thenReturn(questionEntity);
		when(groupDao.checkIfUserHasAccessToGroup(VALID_USER_ID_OF_STUDENT_1, VALID_GROUP_ID_1)).thenReturn(false);
		when(userDetailsDao.getUserDetails(VALID_USER_ID_OF_STUDENT_1)).thenReturn(userEntity);
		discussionServiceImpl.delete(VALID_QUESTION_ID, null, VALID_USER_ID_OF_STUDENT_2, VALID_GROUP_ID_1);
	}
	
	@Test
	public void testDeleteAnswerByPoster() throws UnimitraException {
		when(discussionDao.getAnswerPosterUserId(VALID_ANSWER_ID)).thenReturn(VALID_USER_ID_OF_STUDENT_1);
		HttpStatus expectedValue = HttpStatus.OK;
		HttpStatus actualValue = discussionServiceImpl.delete(null,VALID_ANSWER_ID, VALID_USER_ID_OF_STUDENT_1, null).getStatusCode();
		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void testDeleteAnswerByStaff() throws UnimitraException {
		when(discussionDao.getAnswerPosterUserId(VALID_ANSWER_ID)).thenReturn(VALID_USER_ID_OF_STUDENT_1);
		UserDetailsEntity userEntity = new UserDetailsEntity();
		userEntity.setUserId((Integer)VALID_USER_ID_OF_STAFF);
		userEntity.setUserType("Staff");
		when(userDetailsDao.getUserDetails(VALID_USER_ID_OF_STAFF)).thenReturn(userEntity);
		HttpStatus expectedValue = HttpStatus.OK;
		HttpStatus actualValue = discussionServiceImpl.delete(null,VALID_ANSWER_ID, VALID_USER_ID_OF_STAFF, null).getStatusCode();
		assertEquals(expectedValue, actualValue);
	}

	@Test(expected = UnimitraException.class)
	public void testDeleteAnswerRequestForUnauthorizedUser() throws UnimitraException {
		UserDetailsEntity userEntity = new UserDetailsEntity();
		userEntity.setUserId((Integer)VALID_USER_ID_OF_STUDENT_2);
		userEntity.setUserType("Student");
		when(discussionDao.getAnswerPosterUserId(VALID_ANSWER_ID)).thenReturn(VALID_USER_ID_OF_STUDENT_1);
		when(userDetailsDao.getUserDetails(VALID_USER_ID_OF_STUDENT_2)).thenReturn(userEntity);
		discussionServiceImpl.delete(null, VALID_ANSWER_ID, VALID_USER_ID_OF_STUDENT_2, null);
	}
	
	@Test(expected = UnimitraException.class)
	public void testDeleteRequestForSendingBothQuestionAndAnswerIdSimeltaneously() throws UnimitraException {
		discussionServiceImpl.delete(VALID_QUESTION_ID, VALID_ANSWER_ID, VALID_USER_ID_OF_STUDENT_1, null);
	}
	
	@Test(expected = UnimitraException.class)
	public void testDeleteRequestForSendingNoValues() throws UnimitraException {
		discussionServiceImpl.delete( null, null, VALID_USER_ID_OF_STUDENT_1, null);
	}
	
	@Test
	public void testCloseDiscussionThread() throws UnimitraException {
		DiscussionModel discussionModel = new DiscussionModel();
		discussionModel.setQuestionId(VALID_QUESTION_ID);
		discussionModel.setUserName(VALID_NAME_OF_STAFF);
		discussionModel.setUserId(VALID_USER_ID_OF_STAFF);
		UserDetailsEntity userEntity = new UserDetailsEntity();
		userEntity.setUserId((Integer)VALID_USER_ID_OF_STAFF);
		userEntity.setUserType("Staff");
		when(userDetailsDao.getUserDetails(VALID_USER_ID_OF_STAFF)).thenReturn(userEntity);
		HttpStatus expectedValue = HttpStatus.OK;
		HttpStatus actualValue = discussionServiceImpl.closeDiscussionThread(discussionModel).getStatusCode();
		assertEquals(expectedValue, actualValue);
	}
	
	@Test(expected = UnimitraException.class)
	public void testCloseDiscussionThreadByUnauthorizedUser() throws UnimitraException {
		DiscussionModel discussionModel = new DiscussionModel();
		discussionModel.setQuestionId(VALID_QUESTION_ID);
		discussionModel.setUserName(VALID_NAME);
		discussionModel.setUserId(VALID_USER_ID_OF_STUDENT_1);
		UserDetailsEntity userEntity = new UserDetailsEntity();
		userEntity.setUserId((Integer)VALID_USER_ID_OF_STUDENT_1);
		userEntity.setUserType("Student");
		when(userDetailsDao.getUserDetails(VALID_USER_ID_OF_STUDENT_1)).thenReturn(userEntity);
		discussionServiceImpl.closeDiscussionThread(discussionModel).getStatusCode();
	}
	
	/*
	@Test
	public void testGetDiscussions() {
		fail("Not yet implemented");
	}*/
	
	
	private AnswerModel createValidAnswerModel() {
		AnswerModel answerModel = new AnswerModel();
		answerModel.setAnswer(VALID_ANSWER);
		answerModel.setQuestionId(VALID_QUESTION_ID);
		answerModel.setUserId(VALID_USER_ID_OF_STUDENT_1);
		answerModel.setUserName(VALID_NAME);
		return answerModel;
	}


	private QuestionsEntity getQuestionsEntityForDeleteRequestTesting() {
		QuestionsEntity questionEntity = new QuestionsEntity();
		questionEntity.setDiscussionThreadActive(true);
		questionEntity.setQuestionActive(true);
		questionEntity.setQuestionCategoryId(VALID_CATEGORY_ID);
		questionEntity.setQuestionId(VALID_QUESTION_ID);
		questionEntity.setQuestionDescription(VALID_QUESTION);
		questionEntity.setQuestionPostedByUserId((Integer)VALID_USER_ID_OF_STUDENT_1);
		return questionEntity;
	}
	
	private DiscussionModel createValidDiscussionModelForPostQuestion() {
		DiscussionModel discussionModel = new DiscussionModel();
		discussionModel.setQuestion(VALID_QUESTION);
		discussionModel.setUserName(VALID_NAME);
		discussionModel.setUserId(VALID_USER_ID_OF_STUDENT_1);
		discussionModel.setCategory(VALID_CATEGORY);
		return discussionModel;
	}

}
