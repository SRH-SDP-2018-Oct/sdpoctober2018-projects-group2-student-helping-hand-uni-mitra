package com.unimitra.service;

import org.springframework.stereotype.Service;

import com.unimitra.model.UserDetailsModel;

@Service
public interface UserDetailsService {

	UserDetailsModel getUserDetails(int userId);
}
