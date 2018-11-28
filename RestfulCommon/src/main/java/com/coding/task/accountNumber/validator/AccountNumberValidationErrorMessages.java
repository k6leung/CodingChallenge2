package com.coding.task.accountNumber.validator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.coding.task.messages.loading.ClasspathResourceJsonMessageLoader;

@Component
public class AccountNumberValidationErrorMessages {
	
	private static final String errorMessageLocation = "classpath:/errorMessages/accountNumberValidation/accountNumberValidationErrorMessage.json";
	
	protected Map<String, String> errorMessage;

	public AccountNumberValidationErrorMessages() {
		super();
		
		this.errorMessage = new HashMap<String, String>();
	}
	
	@PostConstruct
	public void loadAccountNumberValidationErrorMessage() throws IOException {
		this.errorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(AccountNumberValidationErrorMessages.errorMessageLocation);
	}

	public Map<String, String> getErrorMessage() {
		return errorMessage;
	}
	
}
