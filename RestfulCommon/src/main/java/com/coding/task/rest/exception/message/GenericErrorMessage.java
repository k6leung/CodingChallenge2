package com.coding.task.rest.exception.message;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.coding.task.messages.loading.ClasspathResourceJsonMessageLoader;

@Component
public class GenericErrorMessage {
	
	private static final String errorMessageFolder = "classpath:/errorMessages/genericError/";
	
	private static final String genericErrorMessageFilePath = "genericErrorMessage.json";
	
	protected Map<String, String> genericErrorMessage;
	
	public GenericErrorMessage() {
		super();
		
		this.genericErrorMessage = new HashMap<String, String>();
	}
	
	@PostConstruct
	public void loadErrorMessages() throws IOException {
		this.genericErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(GenericErrorMessage.errorMessageFolder +
																													GenericErrorMessage.genericErrorMessageFilePath);
	}

	public Map<String, String> getGenericErrorMessage() {
		return genericErrorMessage;
	}

}
