package com.unimitra.dao;

import com.unimitra.entity.AnswersEntity;
import com.unimitra.entity.QuestionsEntity;
import com.unimitra.exception.UnimitraException;

public interface DiscussionDao {

	public String postQuestions(QuestionsEntity questionEntity);

	public String postAnswers(AnswersEntity answersEntity);

	public String deleteQuestion(Integer questionId);

	public String deleteAnswer(Integer answerId);

	public int getQuestionPosterUserId(Integer questionId) throws UnimitraException;

	public int getAnswerPosterUserId(Integer questionId) throws UnimitraException;

}
