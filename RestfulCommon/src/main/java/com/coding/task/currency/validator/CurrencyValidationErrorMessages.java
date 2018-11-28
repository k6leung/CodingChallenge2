package com.coding.task.currency.validator;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.coding.task.messages.loading.ClasspathResourceJsonMessageLoader;

@Component
public class CurrencyValidationErrorMessages {
	
	private static final String errorMessageFolder = "classpath:/errorMessages/currencyValidation/";
	
	private static final String emptyOrInvalidCurrencyJsonFile = "emptyOrInvalidCurrency.json";
	
	private static final String unknownCurrencyJsonFile = "unknownCurrency.json";
	
	protected Map<String, String> emptyOrInvalidCurrencyErrorMessage;
	
	protected Map<String, String> unknownCurrencyErrorMessage;
	
	public CurrencyValidationErrorMessages() {
		super();
	}
	
	@PostConstruct
	public void loadErrorMessages() throws IOException {
		this.emptyOrInvalidCurrencyErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(CurrencyValidationErrorMessages.errorMessageFolder +
																																CurrencyValidationErrorMessages.emptyOrInvalidCurrencyJsonFile);

		this.unknownCurrencyErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(CurrencyValidationErrorMessages.errorMessageFolder +
																														CurrencyValidationErrorMessages.unknownCurrencyJsonFile);
	}

	public Map<String, String> getEmptyOrInvalidCurrencyErrorMessage() {
		return emptyOrInvalidCurrencyErrorMessage;
	}

	public Map<String, String> getUnknownCurrencyErrorMessage() {
		return unknownCurrencyErrorMessage;
	}

}
