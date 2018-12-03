package com.unimitra.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.HttpStatus;

public class ExceptionUtilsTest {

	@Test
	public void testGetErrorMessageFromError() {
		String expectedValue = "User does not have access to do the mentioned change";
		String actualValue = ExceptionUtils.getErrorMessageFromError(ErrorCodes.USER_HAS_NO_ACCESS);
		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void testGetHTTPStatusCode() {
		HttpStatus expectedValue = HttpStatus.UNAUTHORIZED;
		HttpStatus actualValue = ExceptionUtils.getHTTPStatusCode(ErrorCodes.USER_HAS_NO_ACCESS);
		assertEquals(expectedValue, actualValue);
	}
}
