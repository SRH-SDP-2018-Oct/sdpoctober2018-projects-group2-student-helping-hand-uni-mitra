package com.unimitra.dao;

import java.util.List;

import com.unimitra.entity.UserDetailsEntity;
import com.unimitra.exception.UnimitraException;

public interface UserDetailsDao {

	UserDetailsEntity getUserDetails (int userId) throws UnimitraException;

	List<?> getUserAnswerDetails(int userId);

	List<?> getUserQuestionDetails(int userId);

	List<?> getUserGroupDetails(int userId);

	List<?> getUserEventDetails(int userId);

}
