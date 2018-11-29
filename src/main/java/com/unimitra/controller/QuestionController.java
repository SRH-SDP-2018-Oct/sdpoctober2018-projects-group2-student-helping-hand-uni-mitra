package com.unimitra.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unimitra.model.DiscussionModel;

@RestController
@RequestMapping("/questions")
public class QuestionController {

	@PostMapping("/post-question")
	public String postQuestions(@RequestBody DiscussionModel question) {
		return "Posted Successfully";
	}

	@PostMapping("/answer-question")
	public String answerQuestions(@RequestBody DiscussionModel answer) {
		return "Answered Successfully";
	}

	@DeleteMapping("/delete")
	public String deleteQuestionsOrAnswers(@RequestParam(required = false) int questionId,
			@RequestParam(required = false) int answerId, @RequestParam String userName) {
		return "Deleted Successfully";
	}

	@PutMapping("/close-thread")
	public String closeThread(@RequestBody DiscussionModel question) {
		return "Closed Successfully";
	}

}
