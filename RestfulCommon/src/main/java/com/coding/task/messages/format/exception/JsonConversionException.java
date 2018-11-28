package com.coding.task.messages.format.exception;

public class JsonConversionException extends RuntimeException {

	private static final long serialVersionUID = 2469318196686841820L;

	public JsonConversionException(String errorMessage) {
		super(errorMessage);
	}
	
	public JsonConversionException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
	
}
