package com.unimitra.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimitra.dao.UserDetailsDao;
import com.unimitra.entity.UserDetailsEntity;
import com.unimitra.exception.UnimitraException;
import com.unimitra.model.UserDetailsModel;
import com.unimitra.service.UserDetailsService;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserDetailsDao userDetailsDao;

	@Override
	public UserDetailsModel getUserDetailsPersonalView(int userId) throws UnimitraException {

		UserDetailsEntity userData = userDetailsDao.getUserDetails(userId);
		List<?> userAnswerData = userDetailsDao.getUserAnswerDetails(userId);
		List<?> userQuestionData = userDetailsDao.getUserQuestionDetails(userId);
		List<?> userEventData = userDetailsDao.getUserEventDetails(userId);
		List<?> userGroupData = userDetailsDao.getUserGroupDetails(userId);

		UserDetailsModel userModelList = new UserDetailsModel();

		buildUserDataObject(userData, userAnswerData, userQuestionData, userEventData, userGroupData, userModelList);

		return userModelList;
	}

	private void buildUserDataObject(UserDetailsEntity userData, List<?> userAnswerData, List<?> userQuestionData,
			List<?> userEventData, List<?> userGroupData, UserDetailsModel userModelList) {
		userModelList.setUserId(userData.getUserId());
		userModelList.setFirstName(userData.getFirstName());
		userModelList.setLastName(userData.getLastName());
		userModelList.setDateOfBirth(userData.getDateOfBirth());
		userModelList.setEmailId(userData.getEmailId());
		userModelList.setEnrollmentId(userData.getEnrollmentId());
		userModelList.setUserDesignation(userData.getUserDesignation());
		userModelList.setUserType(userData.getUserType());
		userModelList.setPhoneNumber(userData.getPhoneNumber());
		userModelList.setAnswerList(userAnswerData);
		userModelList.setQuestionList(userQuestionData);
		userModelList.setGroupList(userGroupData);
		userModelList.setEventsList(userEventData);
	}

	@Override
	public UserDetailsModel getUserDetailsPublicView(int userId) throws UnimitraException {

		UserDetailsEntity userData = userDetailsDao.getUserDetails(userId);
		UserDetailsModel userModelList = new UserDetailsModel();
		
		userModelList.setUserId(userId);
		userModelList.setFirstName(userData.getFirstName());
		userModelList.setLastName(userData.getLastName());
		userModelList.setEmailId(userData.getEmailId());
		userModelList.setUserDesignation(userData.getUserDesignation());
		userModelList.setUserType(userData.getUserType());
		userModelList.setPhoneNumber(userData.getPhoneNumber());
		return userModelList;

	}

}
