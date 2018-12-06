package com.unimitra.dao;

import com.unimitra.entity.AnswersEntity;
import com.unimitra.entity.QuestionsEntity;
import com.unimitra.exception.UnimitraException;

public interface DiscussionDao {

	public void postQuestions(QuestionsEntity questionEntity);

	public void postAnswers(AnswersEntity answersEntity);

	public void deleteQuestion(Integer questionId) throws UnimitraException;

	public void deleteAnswer(Integer answerId) throws UnimitraException;

	public int getQuestionPosterUserId(Integer questionId) throws UnimitraException;

	public int getAnswerPosterUserId(Integer questionId) throws UnimitraException;

	public String getUserType(int userId) throws UnimitraException;

	public void deletAllAnswersOfQuestion(Integer questionId) throws UnimitraException;

	public void closeQuestionThread(int questionId, boolean isDiscussionThreadActive) throws UnimitraException;

	public boolean getStatusOfDiscussionThread(int questionId) throws UnimitraException;

	boolean getStatusOfQuestionDeletion(int questionId) throws UnimitraException;

}
