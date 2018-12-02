package com.unimitra.dao;

import java.util.List;

import com.unimitra.entity.UserDetailsEntity;
import com.unimitra.model.UserDetailsModel;

public interface UserDetailsDao {

	UserDetailsEntity getUserDetails(int userId);

}
