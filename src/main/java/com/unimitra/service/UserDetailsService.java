package com.unimitra.service;

import org.springframework.stereotype.Service;

import com.unimitra.exception.UnimitraException;
import com.unimitra.model.UserDetailsModel;

@Service
public interface UserDetailsService {

	UserDetailsModel getUserDetailsPersonalView(int userId) throws UnimitraException;
	
	UserDetailsModel getUserDetailsPublicView(int userId) throws UnimitraException;
}
