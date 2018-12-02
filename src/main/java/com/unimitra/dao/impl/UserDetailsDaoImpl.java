package com.unimitra.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.unimitra.dao.UserDetailsDao;
import com.unimitra.entity.UserDetailsEntity;
import com.unimitra.model.UserDetailsModel;

@Repository
public class UserDetailsDaoImpl implements UserDetailsDao {

	SessionFactory sessionFactory;

	@Override
	public UserDetailsEntity getUserDetails(int userId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(UserDetailsEntity.class,userId);
		
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	

}
