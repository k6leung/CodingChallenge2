package com.coding.task.transaction.transaction.validator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.coding.task.messages.loading.ClasspathResourceJsonMessageLoader;

@Component
public class TransactionRequestErrorMessages {
	
	@Value("${error.message.root.folder}")
	protected String errorMessageRootFolder;
	
	@Value("${empty.invalid.transaction.request.error.message.file.path}")
	protected String emptyInvalidTransactionRequestErrorMessageFilePath;
	
	@Value("${failed.transaction.recovered.error.message.file.path}")
	protected String failedTransactionRecoveredErrorMessageFilePath;
	
	@Value("${failed.transaction.unrecovered.error.message.file.path}")
	protected String failedTransactionUnrecoveredErrorMessageFilePath;
	
	protected Map<String, String> emptyInvalidTransactionRequestErrorMessage;
	
	protected Map<String, String> failedTransactionRecoveredErrorMessage;
	
	protected Map<String, String> failedTransactionUnrecoveredErrorMessage;
	
	public TransactionRequestErrorMessages() {
		super();
		
		this.emptyInvalidTransactionRequestErrorMessage = new HashMap<String, String>();
		this.failedTransactionRecoveredErrorMessage = new HashMap<String, String>();
		this.failedTransactionUnrecoveredErrorMessage = new HashMap<String, String>();
	}

	@PostConstruct
	public void loadErrorMessages() throws IOException {
		this.emptyInvalidTransactionRequestErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																																		this.emptyInvalidTransactionRequestErrorMessageFilePath);
		
		this.failedTransactionRecoveredErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																																	this.failedTransactionRecoveredErrorMessageFilePath);
		
		this.failedTransactionUnrecoveredErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																																		this.failedTransactionUnrecoveredErrorMessageFilePath);
	}

	public Map<String, String> getEmptyInvalidTransactionRequestErrorMessage() {
		return emptyInvalidTransactionRequestErrorMessage;
	}

	public Map<String, String> getFailedTransactionRecoveredErrorMessage() {
		return failedTransactionRecoveredErrorMessage;
	}

	public Map<String, String> getFailedTransactionUnrecoveredErrorMessage() {
		return failedTransactionUnrecoveredErrorMessage;
	}
	
}
