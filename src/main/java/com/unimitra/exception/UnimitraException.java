package com.unimitra.exception;

public class UnimitraException extends Exception {

	private static final long serialVersionUID = -1109307894927940472L;

	public UnimitraException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnimitraException(String message) {
		super(message);
	}
}
