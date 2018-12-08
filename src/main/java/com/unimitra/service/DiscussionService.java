package com.unimitra.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.unimitra.exception.UnimitraException;
import com.unimitra.model.AnswerModel;
import com.unimitra.model.DiscussionModel;

public interface DiscussionService {
	
	public ResponseEntity<String> postQuestion(DiscussionModel discussionModel) throws UnimitraException;

	public ResponseEntity<String> answerQuestion(AnswerModel answerModel) throws UnimitraException;

	public ResponseEntity<String> delete(Integer questionId, Integer answerId, Integer userId, Integer groupId) throws UnimitraException;
	
	public ResponseEntity<String> closeDiscussionThread(DiscussionModel discussionModel) throws UnimitraException;

	public ResponseEntity<List<DiscussionModel>> getDiscussions(String searchString, String category, String groupName, Integer userId) throws UnimitraException;
}
