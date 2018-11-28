package com.unimitra.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionController {

	@PostMapping("/post-question")
	public String postQuestions(@RequestBody String question) {
		return "Posted Successfully";
	}

}
