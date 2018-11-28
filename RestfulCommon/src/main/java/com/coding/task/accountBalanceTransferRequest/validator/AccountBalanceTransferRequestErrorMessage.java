package com.coding.task.accountBalanceTransferRequest.validator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.coding.task.messages.loading.ClasspathResourceJsonMessageLoader;

@Component
public class AccountBalanceTransferRequestErrorMessage {
	
	public static final String errorMessageFolder = "classpath:/errorMessages/transferRequestValidation/";
	
	public static final String emptyOrInvalidRequestErrorMessageFilePath = "emptyInvalidRequest.json";
	
	public Map<String, String> emptyOrInvalidRequestErrorMessage;
	
	public AccountBalanceTransferRequestErrorMessage() {
		super();
		
		this.emptyOrInvalidRequestErrorMessage = new HashMap<String, String>();
	}
	
	@PostConstruct
	public void loadErrorMessages() throws IOException {
		this.emptyOrInvalidRequestErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(AccountBalanceTransferRequestErrorMessage.errorMessageFolder + 
																																AccountBalanceTransferRequestErrorMessage.emptyOrInvalidRequestErrorMessageFilePath);
	}

	public Map<String, String> getEmptyOrInvalidRequestErrorMessage() {
		return emptyOrInvalidRequestErrorMessage;
	}

}
