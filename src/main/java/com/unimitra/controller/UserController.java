package com.unimitra.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unimitra.exception.UnimitraException;
import com.unimitra.model.UserDetailsModel;
import com.unimitra.service.UserDetailsService;
import com.unimitra.utility.UnimitraConstants;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserDetailsService userDetailsService;

	private static final Logger LOGGER = LogManager.getLogger();

	@GetMapping("/personal-view")
	public UserDetailsModel getPersonalDetails(@RequestParam int userId) throws UnimitraException {
		UserDetailsModel userDetailPersonalView = userDetailsService.getUserDetailsPersonalView(userId);
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + userDetailPersonalView.toString());
		return ObjectUtils.isEmpty(userDetailPersonalView) ? null : userDetailPersonalView;
	}

	@GetMapping("/public-view")
	public UserDetailsModel getPublicUserDetails(@RequestParam int userId) throws UnimitraException {
		UserDetailsModel userDetailPublicView = userDetailsService.getUserDetailsPublicView(userId);
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + userDetailPublicView.toString());
		return ObjectUtils.isEmpty(userDetailPublicView) ? null : userDetailPublicView;

	}

}
