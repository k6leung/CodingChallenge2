package com.coding.task.rest.exception.response;

import java.io.Serializable;
import java.util.Map;

public class WebExceptionRestfulResponse implements Serializable {

	private static final long serialVersionUID = 5805836751407252353L;
	
	protected String errorCode;
	
	protected Map<String, String> errorMessages;
	
	public WebExceptionRestfulResponse() {
		super();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Map<String, String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(Map<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

}
