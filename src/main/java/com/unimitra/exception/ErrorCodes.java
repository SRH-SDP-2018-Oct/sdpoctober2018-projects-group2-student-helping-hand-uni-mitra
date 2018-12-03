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

}
