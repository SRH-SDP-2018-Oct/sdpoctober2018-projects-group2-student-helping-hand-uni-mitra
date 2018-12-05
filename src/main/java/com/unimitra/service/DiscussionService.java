package com.unimitra.service;

import org.springframework.http.ResponseEntity;

import com.unimitra.exception.UnimitraException;
import com.unimitra.model.AnswerModel;
import com.unimitra.model.DiscussionModel;

public interface DiscussionService {
	
	public ResponseEntity<String> postQuestion(DiscussionModel discussionModel);

	public ResponseEntity<String> answerQuestion(AnswerModel answerModel);

	public ResponseEntity<String> delete(Integer questionId, Integer answerId, Integer userId) throws UnimitraException;
	
}
