package com.unimitra.service;

import org.springframework.http.ResponseEntity;

import com.unimitra.model.DiscussionModel;

public interface DiscussionService {
	
	public ResponseEntity<String> postQuestion(DiscussionModel discussionModel);
	
}
