package com.coding.task.account.validator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.coding.task.messages.loading.ClasspathResourceJsonMessageLoader;

@Component
public class AccountValidationErrorMessages {

	@Value("${error.message.root.folder}")
	protected String errorMessageRootFolder;
	
	@Value("${account.not.found.error.message.file.path}")
	protected String accountNotFoundErrorMessageFilePath;
	
	@Value("${credit.unsupported.currency.error.message.file.path}")
	protected String creditUnsupportedCurrencyErrorMessageFilePath;
	
	@Value("${debit.insufficient.fund.error.message.file.path}")
	protected String debitInsufficientFundErrorMessageFilePath;
	
	@Value("${debit.unsupported.currency.error.message.file.path}")
	protected String debitUnsupportedCurrencyErrorMessageFilePath;
	
	@Value("${account.id.not.found.error.message.file.path}")
	protected String accountIdNotFoundErrorMessageFilePath;
	
	@Value("${invalid.account.record.error.message.file.path}")
	protected String invalidAccountRecordErrorMessageFilePath;
	
	@Value("${empty.invalid.account.id.error.message.file.path}")
	protected String emptyInvalidAccountIdErrorMessageFilePath;
	
	protected Map<String, String> accountNotFoundErrorMessage;
	
	protected Map<String, String> creditUnsupportedCurrencyErrorMessage;
	
	protected Map<String, String> debitInsufficientFundErrorMessage;
	
	protected Map<String, String> debitUnsupportedCurrencyErrorMessage;
	
	protected Map<String, String> accountIdNotFoundErrorMessage;
	
	protected Map<String, String> invalidAccountRecordErrorMessage;
	
	protected Map<String, String> emptyInvalidAccountIdErrorMessage;
	
	public AccountValidationErrorMessages() {
		super();
		
		this.accountNotFoundErrorMessage = new HashMap<String, String>();
		this.creditUnsupportedCurrencyErrorMessage = new HashMap<String, String>();
		this.debitInsufficientFundErrorMessage = new HashMap<String, String>();
		this.debitUnsupportedCurrencyErrorMessage = new HashMap<String, String>();
		this.accountIdNotFoundErrorMessage = new HashMap<String, String>();
		this.invalidAccountRecordErrorMessage = new HashMap<String, String>();
		this.emptyInvalidAccountIdErrorMessage = new HashMap<String, String>();
	}
	
	@PostConstruct
	public void loadErrorMessages() throws IOException {
		this.accountNotFoundErrorMessage =  ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																															this.accountNotFoundErrorMessageFilePath);
		
		this.creditUnsupportedCurrencyErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																																	this.creditUnsupportedCurrencyErrorMessageFilePath);
		
		this.debitInsufficientFundErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																																this.debitInsufficientFundErrorMessageFilePath);
		
		this.debitUnsupportedCurrencyErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																																	this.debitUnsupportedCurrencyErrorMessageFilePath);
		
		this.accountIdNotFoundErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																															this.accountIdNotFoundErrorMessageFilePath);
		
		this.invalidAccountRecordErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																																this.invalidAccountRecordErrorMessageFilePath);
		
		this.emptyInvalidAccountIdErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(this.errorMessageRootFolder +
																																this.emptyInvalidAccountIdErrorMessageFilePath);
	}

	public Map<String, String> getAccountNotFoundErrorMessage() {
		return accountNotFoundErrorMessage;
	}

	public Map<String, String> getCreditUnsupportedCurrencyErrorMessage() {
		return creditUnsupportedCurrencyErrorMessage;
	}

	public Map<String, String> getDebitInsufficientFundErrorMessage() {
		return debitInsufficientFundErrorMessage;
	}

	public Map<String, String> getDebitUnsupportedCurrencyErrorMessage() {
		return debitUnsupportedCurrencyErrorMessage;
	}

	public Map<String, String> getAccountIdNotFoundErrorMessage() {
		return accountIdNotFoundErrorMessage;
	}

	public Map<String, String> getInvalidAccountRecordErrorMessage() {
		return invalidAccountRecordErrorMessage;
	}

	public Map<String, String> getEmptyInvalidAccountIdErrorMessage() {
		return emptyInvalidAccountIdErrorMessage;
	}
	
}
