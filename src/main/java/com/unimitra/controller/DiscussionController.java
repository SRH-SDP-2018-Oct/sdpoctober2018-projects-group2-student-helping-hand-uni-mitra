package com.unimitra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unimitra.exception.UnimitraException;
import com.unimitra.model.AnswerModel;
import com.unimitra.model.DiscussionModel;
import com.unimitra.service.DiscussionService;

@RestController
@RequestMapping("/discussion")
public class DiscussionController {

	@Autowired
	DiscussionService discussionService;

	@PostMapping("/post-question")
	public ResponseEntity<String> postQuestions(@RequestBody DiscussionModel question) throws UnimitraException {
		return discussionService.postQuestion(question);
	}

	@PostMapping("/answer-question")
	public ResponseEntity<String> answerQuestions(@RequestBody AnswerModel answer) {
		return discussionService.answerQuestion(answer);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteQuestionsOrAnswers(@RequestParam(required = false) Integer questionId,
			@RequestParam(required = false) Integer answerId, @RequestParam Integer userId) throws UnimitraException {
		return discussionService.delete(questionId, answerId, userId);
	}

	@PutMapping("/close-thread")
	public String closeThread(@RequestBody DiscussionModel question) {
		return "Closed Successfully";
	}

	@GetMapping("/search")
	public String detailedSearch(@RequestParam String searchString, @RequestParam String category,
			@RequestParam String groupName, @RequestParam int userId) {
		return "Closed Successfully";
	}
}
