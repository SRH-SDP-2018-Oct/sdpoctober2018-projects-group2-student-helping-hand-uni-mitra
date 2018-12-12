package com.unimitra.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCodes {

	public static final String USER_HAS_NO_ACCESS = "UM1001 " + HttpStatus.UNAUTHORIZED.value()
			+ ": User does not have access to do the mentioned change";
	public static final String INVALID_REQUEST = "UM1002 " + HttpStatus.BAD_REQUEST.value()
			+ ": Invalid Request or Request Parameters";

	public static final String USER_NOT_PRESENT = "UM1003 " + HttpStatus.BAD_REQUEST.value()
			+ ": Given user does not exist";
	public static final String INVALID_DELETE_DISCUSSION_REQUEST = "UM1004 " + HttpStatus.BAD_REQUEST.value()
			+ ": Invalid Request";
	public static final String QUESTION_NOT_PRESENT = "UM1005 " + HttpStatus.BAD_REQUEST.value()
			+ ": Given question does not exist";
	public static final String ANSWER_NOT_PRESENT = "UM1005 " + HttpStatus.BAD_REQUEST.value()
			+ ": Given answer does not exist";
	public static final String CATEGORY_NOT_PRESENT = "UM1006 " + HttpStatus.BAD_REQUEST.value()
			+ ": Given category does not exist";

	public static final String EVENT_NOT_PRESENT = "UM1007 " + HttpStatus.BAD_REQUEST.value()
			+ ": There is no upcoming events";
	public static final String EVENT_NOT_PRESENT_FOR_EVENTID = "UM1008 " + HttpStatus.BAD_REQUEST.value()
			+ ": There is no event for provided input";
	public static final String MAPPING_NOT_PRESENT_FOR_EVENTID_USERID = "UM1009 " + HttpStatus.BAD_REQUEST.value()
			+ ": User is not registered for given event";
	public static final String GROUP_NOT_PRESENT_FOR_GROUPID = "UM1010 " + HttpStatus.BAD_REQUEST.value()
			+ ": Group not present for provided input";
	public static final String DUPLICATE_GROUP_NAME = "UM1011 " + HttpStatus.BAD_REQUEST.value()
			+ ": Group already exsist";

	public static final String QUESTION_THREAD_INACTIVE = "UM1012 " + HttpStatus.BAD_REQUEST.value()
			+ ": The question thread is inactive so you cannot answer";
	public static final String USER_DOES_NOT_HAVE_ACCESS_TO_GROUP = "UM1013 " + HttpStatus.UNAUTHORIZED.value()
			+ ": The user has no access to group";
	public static final String INVALID_SEARCH_REQUEST = "UM1014 " + HttpStatus.UNAUTHORIZED.value()
			+ ": Invalid search discussion request";
	public static final String NO_RESULTS_FOUND = "UM1015 " + HttpStatus.NOT_FOUND.value() + ": No results found";
	public static final String GROUP_DOES_NOT_EXIST = "UM1016 " + HttpStatus.NOT_FOUND.value()
			+ ": Group does not exist";
			
	public static final String GROUP_NOT_PRESENT = "UM1017 " + HttpStatus.BAD_REQUEST.value()
	+ ": Incorrect group name";
	public static final String NO_GROUP_APPROVAL_REQUEST = "UM1018 " + HttpStatus.NO_CONTENT.value()
	+ ": No group approval request found";
	public static final String USER_HAS_NO_ACCESS_TO_ADD_MEMBER_TO_GROUP = "UM1019 " + HttpStatus.UNAUTHORIZED.value()
	+ ": User has no access to add a member to the group as logged-in is not a admin.";
	
	public static final String USER_HAS_NO_ACCESS_FOR_JAPSER_REPORT = "UM1019 " + HttpStatus.UNAUTHORIZED.value()
	+ ": You do not have access to generate Report. Only Staff members can generate Report.";
	
	public static final String USER_IS_NOT_AUTHORISED = "UM1020 " + HttpStatus.UNAUTHORIZED.value()
	+ ": You are not authorised  to delete/edit the event.";
}