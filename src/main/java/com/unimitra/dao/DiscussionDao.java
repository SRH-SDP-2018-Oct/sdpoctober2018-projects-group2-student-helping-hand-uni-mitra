package com.unimitra.dao;

import java.util.List;

import com.unimitra.entity.AnswersEntity;
import com.unimitra.entity.QuestionsEntity;
import com.unimitra.exception.UnimitraException;
import com.unimitra.model.DiscussionModel;

public interface DiscussionDao {

	public void postQuestions(QuestionsEntity questionEntity);

	public void postAnswers(AnswersEntity answersEntity);

	public void deleteQuestion(Integer questionId) throws UnimitraException;

	public void deleteAnswer(Integer answerId) throws UnimitraException;

	public int getAnswerPosterUserId(Integer questionId) throws UnimitraException;

	public String getUserType(int userId) throws UnimitraException;

	public void deletAllAnswersOfQuestion(Integer questionId) throws UnimitraException;

	public void closeQuestionThread(int questionId, boolean isDiscussionThreadActive) throws UnimitraException;

	public boolean checkIfUserHasAccessToGroup(int userId, int questionGroupId) throws UnimitraException;

	public List<DiscussionModel> searchOnKeyword(String searchString) throws UnimitraException;

	public List<DiscussionModel> searchOnCategory(Integer categoryId) throws UnimitraException;

	public List<DiscussionModel> searchOnKeywordInGroup(String searchString, int groupId, int userId);

	public List<DiscussionModel> searchOnCategoryInGroup(int categoryid, int groupId);
	
	public int getGroupIdFromGroupName(String groupName) throws UnimitraException;

	public QuestionsEntity getQuestionEntityFromQuestionId(int questionId) throws UnimitraException;

	

}
