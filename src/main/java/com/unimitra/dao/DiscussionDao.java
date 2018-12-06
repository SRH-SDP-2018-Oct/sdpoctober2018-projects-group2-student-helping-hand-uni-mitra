package com.unimitra.dao;

import com.unimitra.entity.AnswersEntity;
import com.unimitra.entity.QuestionsEntity;
import com.unimitra.exception.UnimitraException;

public interface DiscussionDao {

	public void postQuestions(QuestionsEntity questionEntity);

	public void postAnswers(AnswersEntity answersEntity);

	public void deleteQuestion(Integer questionId);

	public void deleteAnswer(Integer answerId);

	public int getQuestionPosterUserId(Integer questionId) throws UnimitraException;

	public int getAnswerPosterUserId(Integer questionId) throws UnimitraException;

	public String getUserType(int userId) throws UnimitraException;

	public void deletAllAnswersOfQuestion(Integer questionId);

}
