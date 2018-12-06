package com.unimitra.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.unimitra.dao.DiscussionDao;
import com.unimitra.entity.AnswersEntity;
import com.unimitra.entity.QuestionsEntity;
import com.unimitra.entity.UserDetailsEntity;
import com.unimitra.exception.ErrorCodes;
import com.unimitra.exception.UnimitraException;

@Repository
public class DiscussionDaoImpl implements DiscussionDao {

	SessionFactory sessionFactory;

	@Override
	public void postQuestions(QuestionsEntity questionEntity) {
		Session session = sessionFactory.getCurrentSession();
		session.save(questionEntity);
	}

	@Override
	public void postAnswers(AnswersEntity answersEntity) {
		Session session = sessionFactory.getCurrentSession();
		session.save(answersEntity);
	}

	@Override
	public void deleteQuestion(Integer questionId) {
		Session session = sessionFactory.getCurrentSession();
		QuestionsEntity questionEntity = session.get(QuestionsEntity.class, questionId);
		questionEntity.setQuestionActive(false);
		session.update(questionEntity);
	}

	@Override
	public void deleteAnswer(Integer answerId) {
		Session session = sessionFactory.getCurrentSession();
		AnswersEntity answersEntity = session.get(AnswersEntity.class, answerId);
		answersEntity.setAnswerIsActive(false);
		session.update(answersEntity);
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
		return questionEntity.getQuestionPostedByUserId();
	}

	private void nullCheckForEntity(Object entity, String errorCode) throws UnimitraException {
		if (ObjectUtils.isEmpty(entity)) {
			throw new UnimitraException(errorCode);
		}
	}

	@Override
	public int getAnswerPosterUserId(Integer answerId) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		AnswersEntity answersEntity = session.get(AnswersEntity.class, answerId);
		nullCheckForEntity(answersEntity, ErrorCodes.ANSWER_NOT_PRESENT);
		return answersEntity.getAnswerPostedByUserId();
	}

	@Override
	public String getUserType(int userId) throws UnimitraException {
		// Add This Method in UserDao
		Session session = sessionFactory.getCurrentSession();
		UserDetailsEntity userEntity = session.get(UserDetailsEntity.class, userId);
		nullCheckForEntity(userEntity, ErrorCodes.USER_NOT_PRESENT);
		return userEntity.getUserType();
	}

	@Override
	public void deletAllAnswersOfQuestion(Integer questionId) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<AnswersEntity> criteriaQuery = criteriaBuilder.createQuery(AnswersEntity.class);
		Root<AnswersEntity> questionsRoot = criteriaQuery.from(AnswersEntity.class);
		criteriaQuery.select(questionsRoot);
		criteriaQuery.where(criteriaBuilder.equal(questionsRoot.get("questionId"), questionId));
		List<AnswersEntity> listOfAnswersWithGivenQuestionId = session.createQuery(criteriaQuery).getResultList();
		if (!CollectionUtils.isEmpty(listOfAnswersWithGivenQuestionId)) {
			for (AnswersEntity answer : listOfAnswersWithGivenQuestionId) {
				deleteAnswer(answer.getAnswerId());
			}
		}

	}

}
