package com.unimitra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unimitra.exception.UnimitraException;
import com.unimitra.model.UserDetailsModel;
import com.unimitra.service.UserDetailsService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserDetailsService userDetailsService;

	@GetMapping("/personal-view")
	public UserDetailsModel getPersonalDetails(@RequestParam int userId) throws UnimitraException {
		UserDetailsModel userDetailPersonalView = userDetailsService.getUserDetailsPersonalView(userId);
		return ObjectUtils.isEmpty(userDetailPersonalView) ? null : userDetailPersonalView;
	}

	@GetMapping("/public-view")
	public UserDetailsModel getPublicUserDetails(@RequestParam int userId) throws UnimitraException {
		UserDetailsModel userDetailPublicView = userDetailsService.getUserDetailsPublicView(userId);
		return ObjectUtils.isEmpty(userDetailPublicView) ? null : userDetailPublicView;

	}

}
