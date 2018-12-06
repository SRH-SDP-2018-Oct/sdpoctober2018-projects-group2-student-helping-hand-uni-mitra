package com.unimitra.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.unimitra.dao.CategoryDao;
import com.unimitra.entity.CategoryEntity;
import com.unimitra.exception.ErrorCodes;
import com.unimitra.exception.UnimitraException;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	SessionFactory sessionFactory;

	@Override
	public int getCategoryIdFromCategoryName(String categoryName) throws UnimitraException {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<CategoryEntity> criteriaQuery = criteriaBuilder.createQuery(CategoryEntity.class);
		Root<CategoryEntity> questionsRoot = criteriaQuery.from(CategoryEntity.class);
		criteriaQuery.select(questionsRoot);
		criteriaQuery.where(criteriaBuilder.equal(questionsRoot.get("categoryName"), categoryName));
		Integer categoryId = session.createQuery(criteriaQuery).getSingleResult().getCategoryId();
		if (ObjectUtils.isEmpty(categoryId)) {
			throw new UnimitraException(ErrorCodes.CATEGORY_NOT_PRESENT);
		}
		return categoryId;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
