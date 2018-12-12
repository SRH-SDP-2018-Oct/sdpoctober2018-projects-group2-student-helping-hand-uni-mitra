package com.unimitra.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.unimitra.dao.DiscussionDao;
import com.unimitra.entity.AnswersEntity;
import com.unimitra.entity.QuestionsEntity;
import com.unimitra.exception.ErrorCodes;
import com.unimitra.exception.UnimitraException;
import com.unimitra.model.DiscussionModel;
import com.unimitra.utility.UnimitraUtility;

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
	public void deleteQuestion(Integer questionId) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		QuestionsEntity questionEntity = getQuestionEntity(questionId, session);
		questionEntity.setQuestionActive(false);
		session.update(questionEntity);
	}

	@Override
	public void deleteAnswer(Integer answerId) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		AnswersEntity answersEntity = getAnswersEntity(answerId, session);
		answersEntity.setAnswerIsActive(false);
		session.update(answersEntity);
	}

	@Override
	public int getAnswerPosterUserId(Integer answerId) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		return getAnswersEntity(answerId, session).getAnswerPostedByUserId();
	}

	@Override
	public void deletAllAnswersOfQuestion(Integer questionId) throws UnimitraException {
		List<AnswersEntity> listOfAnswersWithGivenQuestionId = getListOfAnswersBasedOnQuestionId(questionId);
		if (!CollectionUtils.isEmpty(listOfAnswersWithGivenQuestionId)) {
			for (AnswersEntity answer : listOfAnswersWithGivenQuestionId) {
				deleteAnswer(answer.getAnswerId());
			}
		}
	}

	@Override
	public void closeQuestionThread(int questionId, boolean isDiscussionThreadActive) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		QuestionsEntity questionEntity = getQuestionEntity(questionId, session);
		questionEntity.setDiscussionThreadActive(isDiscussionThreadActive);
		session.update(questionEntity);
	}

	@Override
	public QuestionsEntity getQuestionEntityFromQuestionId(int questionId) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		return getQuestionEntity(questionId, session);
	}

	@Override
	public List<DiscussionModel> searchOnKeyword(String searchString) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<QuestionsEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionsEntity.class);
		Root<QuestionsEntity> questionsRoot = criteriaQuery.from(QuestionsEntity.class);
		criteriaQuery.select(questionsRoot);
		EntityType<QuestionsEntity> type = session.getMetamodel().entity(QuestionsEntity.class);

		criteriaQuery.where(
				criteriaBuilder.like(
						criteriaBuilder.lower(questionsRoot.get(type.getDeclaredSingularAttribute("questionDescription", String.class))),"%" + searchString.toLowerCase() + "%"),
						criteriaBuilder.equal(questionsRoot.get("isQuestionActive"), true));

		List<QuestionsEntity> listOfQuestionsWithGivenCategory = session.createQuery(criteriaQuery).getResultList();
		getListOfDiscussionModelFromQuestionsEntity(listOfQuestionsWithGivenCategory);
		return getListOfDiscussionModelFromQuestionsEntity(listOfQuestionsWithGivenCategory);
	}

	@Override
	public List<DiscussionModel> searchOnCategory(Integer categoryId) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<QuestionsEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionsEntity.class);
		Root<QuestionsEntity> questionsRoot = criteriaQuery.from(QuestionsEntity.class);
		criteriaQuery.select(questionsRoot);
		criteriaQuery.where(criteriaBuilder.equal(questionsRoot.get("questionCategoryId"), categoryId),
				criteriaBuilder.equal(questionsRoot.get("isQuestionActive"), true));
		List<QuestionsEntity> listOfQuestionsWithGivenCategory = session.createQuery(criteriaQuery).getResultList();
		return getListOfDiscussionModelFromQuestionsEntity(listOfQuestionsWithGivenCategory);
	}

	@Override
	public List<DiscussionModel> searchOnKeywordInGroup(String searchString, int groupId, int userId) {

		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<QuestionsEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionsEntity.class);
		Root<QuestionsEntity> questionsRoot = criteriaQuery.from(QuestionsEntity.class);
		criteriaQuery.select(questionsRoot);
		EntityType<QuestionsEntity> type = session.getMetamodel().entity(QuestionsEntity.class);

		criteriaQuery.where(
				criteriaBuilder.like(
						criteriaBuilder.lower(questionsRoot
								.get(type.getDeclaredSingularAttribute("questionDescription", String.class))),
						"%" + searchString.toLowerCase() + "%"),
				criteriaBuilder.equal(questionsRoot.get("questionGroupId"), groupId),
				criteriaBuilder.equal(questionsRoot.get("isQuestionActive"), true));

		List<QuestionsEntity> listOfQuestionsWithGivenCategory = session.createQuery(criteriaQuery).getResultList();
		return getListOfDiscussionModelFromQuestionsEntity(listOfQuestionsWithGivenCategory);
	}

	@Override
	public List<DiscussionModel> searchOnCategoryInGroup(int categoryid, int groupId) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<QuestionsEntity> criteriaQuery = criteriaBuilder.createQuery(QuestionsEntity.class);
		Root<QuestionsEntity> questionsRoot = criteriaQuery.from(QuestionsEntity.class);
		criteriaQuery.select(questionsRoot);
		criteriaQuery.where(criteriaBuilder.equal(questionsRoot.get("questionCategoryId"), categoryid),
				criteriaBuilder.equal(questionsRoot.get("isQuestionActive"), true),
				criteriaBuilder.equal(questionsRoot.get("questionGroupId"), groupId));
		List<QuestionsEntity> listOfQuestionsWithGivenCategory = session.createQuery(criteriaQuery).getResultList();
		return getListOfDiscussionModelFromQuestionsEntity(listOfQuestionsWithGivenCategory);
	}

	private List<DiscussionModel> getListOfDiscussionModelFromQuestionsEntity(
			List<QuestionsEntity> listOfQuestionsWithGivenCategory) {
		List<DiscussionModel> listOfDiscussionModel = new ArrayList<>();
		
		listOfQuestionsWithGivenCategory.forEach(questionEntity -> {
			DiscussionModel discussionModel = new DiscussionModel();
			discussionModel.setQuestionId(questionEntity.getQuestionId());
			discussionModel.setQuestion(questionEntity.getQuestionDescription());
			discussionModel.setUserId(questionEntity.getQuestionPostedByUserId());
			discussionModel.setDiscussionThreadActive(questionEntity.isDiscussionThreadActive());
			List<AnswersEntity> listOfAnswers = getListOfAnswersBasedOnQuestionId(questionEntity.getQuestionId());
			discussionModel.setAnswer(listOfAnswers);
			discussionModel.setCategoryId(questionEntity.getQuestionCategoryId());
			discussionModel.setGroupId(questionEntity.getQuestionGroupId());
			discussionModel.setTimestamp(questionEntity.getQuestionCreationDateTime());
			listOfDiscussionModel.add(discussionModel);
		});
		return listOfDiscussionModel;
	}

	private List<AnswersEntity> getListOfAnswersBasedOnQuestionId(Integer questionId) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<AnswersEntity> criteriaQuery = criteriaBuilder.createQuery(AnswersEntity.class);
		Root<AnswersEntity> questionsRoot = criteriaQuery.from(AnswersEntity.class);
		criteriaQuery.select(questionsRoot);
		criteriaQuery.where(criteriaBuilder.equal(questionsRoot.get("questionId"), questionId),
							criteriaBuilder.equal(questionsRoot.get("answerIsActive"), true));
		return session.createQuery(criteriaQuery).getResultList();
	}

	private AnswersEntity getAnswersEntity(Integer answerId, Session session) throws UnimitraException {
		AnswersEntity answersEntity = session.get(AnswersEntity.class, answerId);
		UnimitraUtility.nullCheckForEntity(answersEntity, ErrorCodes.ANSWER_NOT_PRESENT);
		return answersEntity;
	}

	private QuestionsEntity getQuestionEntity(Integer questionId, Session session) throws UnimitraException {
		QuestionsEntity questionEntity = session.get(QuestionsEntity.class, questionId);
		UnimitraUtility.nullCheckForEntity(questionEntity, ErrorCodes.QUESTION_NOT_PRESENT);
		return questionEntity;
	}

	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
