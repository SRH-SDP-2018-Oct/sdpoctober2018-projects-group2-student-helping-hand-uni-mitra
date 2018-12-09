package com.unimitra.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.unimitra.dao.CategoryDao;
import com.unimitra.dao.DiscussionDao;
import com.unimitra.dao.GroupDao;
import com.unimitra.dao.UserDetailsDao;
import com.unimitra.exception.UnimitraException;
import com.unimitra.model.DiscussionModel;

@RunWith(SpringRunner.class)
public class DiscussionServiceTest {

	@InjectMocks
	DiscussionServiceImpl discussionServiceImpl;

	@Mock
	DiscussionDao discussionDao;
	@Mock
	CategoryDao categoryDao;
	@Mock
	UserDetailsDao userDetailsDao;
	@Mock
	GroupDao groupDao;

	public static final String VALID_QUESTION = "Do we have a gym available in Uniersity";
	public static final String VALID_NAME = "Shardul";
	public static final int VALID_USER_ID = 16;
	public static final String VALID_CATEGORY = "Others";
	
	@Test
	public void testPostQuestion() throws UnimitraException {
		DiscussionModel discussionModel = new DiscussionModel();
		discussionModel.setQuestion(VALID_QUESTION);
		discussionModel.setUserName(VALID_NAME);
		discussionModel.setUserId(VALID_USER_ID);
		discussionModel.setCategory(VALID_CATEGORY);
		HttpStatus expectedValue = HttpStatus.CREATED;
		HttpStatus actualValue = discussionServiceImpl.postQuestion(discussionModel).getStatusCode();
		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void testAnswerQuestion() {
		fail("Not yet implemented");
	}

	/*@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testCloseDiscussionThread() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDiscussions() {
		fail("Not yet implemented");
	}*/

}
