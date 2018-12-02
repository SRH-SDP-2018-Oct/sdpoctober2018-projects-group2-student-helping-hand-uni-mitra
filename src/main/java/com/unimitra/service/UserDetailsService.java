package com.unimitra.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.unimitra.model.UserDetailsModel;

@Service
public interface UserDetailsService {

	UserDetailsModel getUserDetails(int userId);
}
