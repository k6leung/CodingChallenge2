package com.coding.task.records.validator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.coding.task.messages.loading.ClasspathResourceJsonMessageLoader;

@Component
public class TransactionRecordErrorMessages {
	
	@Value("${error.message.root.folder}")
	protected String errorMessageRootFolder;
	
	@Value("${credit.record.not.found.error.message.file.path}")
	protected String creditRecordNotFoundErrorMessageFilePath;
	
	@Value("${invalid.credit.record.error.message.file.path}")
	protected String invalidCreditRecordErrorMessageFilePath;
	
	@Value("${debit.record.not.found.error.message.file.path}")
	protected String debitRecordNotFoundErrorMessageFilePath;
	
	@Value("${invalid.debit.record.error.message.file.path}")
	protected String invalidDebitRecordErrormessageFilePath;
	
	@Value("${invalid.transaction.record.error.message.file.path}")
	protected String invalidTransactionRecordErrorMessageFilePath;
	
	protected Map<String, String> creditRecordNotFoundErrorMessage;
	
	protected Map<String, String> invalidCreditRecordErrorMessage;
	
	protected Map<String, String> debitRecordNotFoundErrorMessage;
	
	protected Map<String, String> invalidDebitRecordErrorMessage;
	
	protected Map<String, String> invalidTransactionRecordErrorMessage;
	
	public TransactionRecordErrorMessages() {
		super();
		
		this.creditRecordNotFoundErrorMessage = new HashMap<String, String>();
		this.invalidCreditRecordErrorMessage = new HashMap<String, String>();
		this.debitRecordNotFoundErrorMessage = new HashMap<String, String>();
		this.invalidDebitRecordErrorMessage = new HashMap<String, String>();
		this.invalidTransactionRecordErrorMessage = new HashMap<String, String>();
	}
	
	@PostConstruct
	public void loadErrorMessages() throws IOException {
		this.creditRecordNotFoundErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																																this.creditRecordNotFoundErrorMessageFilePath);
		
		this.invalidCreditRecordErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																															this.invalidCreditRecordErrorMessageFilePath);
		
		this.debitRecordNotFoundErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																															this.debitRecordNotFoundErrorMessageFilePath);
		
		this.invalidDebitRecordErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																															this.invalidDebitRecordErrormessageFilePath);
		
		this.invalidTransactionRecordErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																																	this.invalidTransactionRecordErrorMessageFilePath);
	}

	public Map<String, String> getCreditRecordNotFoundErrorMessage() {
		return creditRecordNotFoundErrorMessage;
	}

	public Map<String, String> getInvalidCreditRecordErrorMessage() {
		return invalidCreditRecordErrorMessage;
	}

	public Map<String, String> getDebitRecordNotFoundErrorMessage() {
		return debitRecordNotFoundErrorMessage;
	}

	public Map<String, String> getInvalidDebitRecordErrorMessage() {
		return invalidDebitRecordErrorMessage;
	}

	public Map<String, String> getInvalidTransactionRecordErrorMessage() {
		return invalidTransactionRecordErrorMessage;
	}

}
