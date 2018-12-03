package com.unimitra.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.unimitra.dao.CategoryDao;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	SessionFactory sessionFactory;

	@Override
	public int getCategoryIdFromCategoryName(String categoryName) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer) session
				.createQuery("SELECT categoryId from CategoryEntity c WHERE c.categoryName='" + categoryName + "'")
				.getSingleResult();
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
