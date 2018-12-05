package com.unimitra.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.unimitra.dao.DiscussionDao;
import com.unimitra.entity.AnswersEntity;
import com.unimitra.entity.QuestionsEntity;
import com.unimitra.exception.ErrorCodes;
import com.unimitra.exception.UnimitraException;

@Repository
public class DiscussionDaoImpl implements DiscussionDao {

	SessionFactory sessionFactory;

	@Override
	public String postQuestions(QuestionsEntity questionEntity) {
		Session session = sessionFactory.getCurrentSession();
		session.save(questionEntity);
		return "Successful";
	}

	@Override
	public String postAnswers(AnswersEntity answersEntity) {
		Session session = sessionFactory.getCurrentSession();
		session.save(answersEntity);
		return "Successful";
	}

	@Override
	public String deleteQuestion(Integer questionId) {
		Session session = sessionFactory.getCurrentSession();
		QuestionsEntity questionEntity = session.get(QuestionsEntity.class, questionId);
		questionEntity.setQuestionActive(false);
		session.update(questionEntity);
		return "Deleted";
	}

	@Override
	public String deleteAnswer(Integer answerId) {
		Session session = sessionFactory.getCurrentSession();
		AnswersEntity answersEntity = session.get(AnswersEntity.class, answerId);
		answersEntity.setAnswerIsActive(false);
		session.update(answersEntity);
		return "Deleted";
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public int getQuestionPosterUserId(Integer questionId) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		QuestionsEntity questionEntity = session.get(QuestionsEntity.class, questionId);
		nullCheckForEntity(questionEntity, ErrorCodes.QUESTION_NOT_PRESENT);
		return session.get(QuestionsEntity.class, questionId).getQuestionPostedByUserId();
	}

	private void nullCheckForEntity(Object questionEntity, String errorCode) throws UnimitraException {
		if (ObjectUtils.isEmpty(questionEntity)) {
			throw new UnimitraException(errorCode);
		}
	}

	@Override
	public int getAnswerPosterUserId(Integer answerId) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		AnswersEntity answersEntity = session.get(AnswersEntity.class, answerId);
		nullCheckForEntity(answersEntity, ErrorCodes.ANSWER_NOT_PRESENT);
		return session.get(AnswersEntity.class, answerId).getAnswerPostedByUserId();
	}

}
