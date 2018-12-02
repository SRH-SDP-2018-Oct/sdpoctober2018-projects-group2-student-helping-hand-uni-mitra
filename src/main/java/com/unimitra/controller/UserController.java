package com.unimitra.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unimitra.model.UserDetailsModel;
import com.unimitra.service.UserDetailsService;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LogManager.getLogger();
	@Autowired
	UserDetailsService userDetailsService;

	@GetMapping("/personal-view")
	public UserDetailsModel getPersonalDetails(@RequestParam int userId, @RequestParam String userName) {
		UserDetailsModel userDetail = userDetailsService.getUserDetails(1);
		LOGGER.info("Requested User Details " + userDetail.toString());
		return ObjectUtils.isEmpty(userDetail) ? null : userDetail;
	}

	@GetMapping("/public-view")
	public UserDetailsModel getPublicUserDetails(@RequestParam int userId, @RequestParam String userName) {
		return null;

	}

}
