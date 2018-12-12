package com.unimitra.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.unimitra.email.EmailUtility;
import com.unimitra.model.UnimitraErrorResponseModel;

@RestControllerAdvice
public class UnimitraRestExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	EmailUtility emailUtil;

	@ExceptionHandler(UnimitraException.class)
	public ResponseEntity<Object> defaultExceptionHandler(Exception exception, WebRequest request) {
		String error = exception.getMessage();
		UnimitraErrorResponseModel errorResponseModel = new UnimitraErrorResponseModel();
		errorResponseModel.setErrorMessage(ExceptionUtils.getErrorMessageFromError(error));
		return handleExceptionInternal(exception, errorResponseModel, new HttpHeaders(),
				ExceptionUtils.getHTTPStatusCode(error), request);

	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<Object> unknownExceptionHandler(Exception exception, WebRequest request) {
		String error = exception.getMessage();
		emailUtil.sendSimpleMessage("unimitra.student.helping.hand@gmail.com", "Error Occured",
				"Error Occured : " + error);
		return handleExceptionInternal(exception, new HttpHeaders(), null, HttpStatus.INTERNAL_SERVER_ERROR, request);

	}
}
