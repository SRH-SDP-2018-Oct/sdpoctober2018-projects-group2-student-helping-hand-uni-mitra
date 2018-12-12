package com.unimitra.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.unimitra.utility.UnimitraConstants;

@RestController
@RequestMapping("/discussion")
public class DiscussionController {

	@Autowired
	DiscussionService discussionService;
	private static final Logger LOGGER = LogManager.getLogger();

	@PostMapping("/post-question")
	public ResponseEntity<String> postQuestions(@RequestBody DiscussionModel questionModel) throws UnimitraException {
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + UnimitraConstants.POST_QUESTION + questionModel.toString());
		return discussionService.postQuestion(questionModel);
	}

	@PostMapping("/answer-question")
	public ResponseEntity<String> answerQuestions(@RequestBody AnswerModel answerModel) throws UnimitraException {
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + UnimitraConstants.ANSWER_QUESTION + answerModel.toString());
		return discussionService.answerQuestion(answerModel);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteQuestionsOrAnswers(@RequestParam(required = false) Integer questionId,
			@RequestParam(required = false) Integer answerId, @RequestParam Integer userId,
			@RequestParam(required = false) Integer groupId) throws UnimitraException {
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + UnimitraConstants.DELETE_DISCUSSION
				+ "questionId {}, answerId {}, userId {}, goupId {}", questionId, answerId, userId, groupId);
		return discussionService.delete(questionId, answerId, userId, groupId);
	}

	@PutMapping("/close-thread")
	public ResponseEntity<String> closeThread(@RequestBody DiscussionModel discussionModel) throws UnimitraException {
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + UnimitraConstants.CLOSE_THREAD + discussionModel.toString());
		return discussionService.closeDiscussionThread(discussionModel);
	}

	@GetMapping("/search")
	public ResponseEntity<List<DiscussionModel>> detailedSearch(@RequestParam(required = false) String searchString,
			@RequestParam(required = false) String category, @RequestParam(required = false) String groupName,
			@RequestParam(required = false) Integer userId) throws UnimitraException {
		LOGGER.info(
				UnimitraConstants.UNI_MITRA_AUDIT + UnimitraConstants.SEARCH_DISCUSSION
						+ "searchString {}, category {}, goupId {}, userId {}",
				searchString, category, groupName, userId);
		return discussionService.getDiscussions(searchString, category, groupName, userId);
	}
}
