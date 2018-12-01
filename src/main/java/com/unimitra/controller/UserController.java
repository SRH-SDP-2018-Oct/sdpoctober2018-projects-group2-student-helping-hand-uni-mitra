package com.unimitra.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unimitra.model.UserDetailsModel;

@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping("/personal-view")
	public UserDetailsModel getPersonalDetails(@RequestParam int userId, @RequestParam String userName) {
		return null;
	}

	@GetMapping("/public-view")
	public UserDetailsModel getPublicUserDetails(@RequestParam int userId, @RequestParam String userName) {
		return null;
	}

}
