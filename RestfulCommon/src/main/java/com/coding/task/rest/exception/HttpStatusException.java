package com.coding.task.rest.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

public class HttpStatusException extends RuntimeException {

	private static final long serialVersionUID = 240909830791834984L;

	protected HttpStatus httpStatus;
	
	protected String errorCode;
	
	protected Map<String, String> errorMessages;

	public HttpStatusException(HttpStatus status, String errorCode, Map<String, String> errorMessages) {
		super(errorCode);
		this.httpStatus = status;
		this.errorCode = errorCode;
		this.errorMessages = errorMessages;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public Map<String, String> getErrorMessages() {
		return errorMessages;
	}
	
}
