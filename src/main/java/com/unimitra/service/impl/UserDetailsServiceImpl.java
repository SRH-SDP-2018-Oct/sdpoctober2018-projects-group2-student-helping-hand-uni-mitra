package com.unimitra.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimitra.dao.UserDetailsDao;
import com.unimitra.entity.UserDetailsEntity;
import com.unimitra.model.UserDetailsModel;
import com.unimitra.service.UserDetailsService;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserDetailsDao userDetailsDao;
	
	@Override
	public UserDetailsModel getUserDetails(int userId) {
		
		UserDetailsEntity userEntity = userDetailsDao.getUserDetails(userId);
		UserDetailsModel userModel = new UserDetailsModel();
		
		userModel.setUserId(userEntity.getUserId());
		userModel.setFirstName(userEntity.getFirstName());
		
		return userModel;
		
	}
}
