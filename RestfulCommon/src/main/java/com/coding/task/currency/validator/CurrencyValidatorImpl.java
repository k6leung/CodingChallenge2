package com.coding.task.currency.validator;

import org.springframework.stereotype.Component;

import com.coding.task.account.currency.Currency;
import com.coding.task.messages.format.ErrorMessageFormatter;
import com.coding.task.rest.errorCode.CommonStatusCode;
import com.coding.task.rest.exception.HttpStatusException;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Component
public class CurrencyValidatorImpl implements CurrencyValidator {
	
	@Autowired
	protected CurrencyValidationErrorMessages errorMessages;
	
	public CurrencyValidatorImpl() {
		super();
	}
	
	public void validate(String currency) {
		if(currency == null) {
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessages.getEmptyOrInvalidCurrencyErrorMessage(), 
																										currency);
			
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											CommonStatusCode.EMPTY_INVALID_CURRENCY,
											formattedErrorMessageMap);
		}
		
		if(!Currency.currencySet.contains(currency.toUpperCase())) {//unknown currency
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessages.getUnknownCurrencyErrorMessage(), 
																										currency);
			
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											CommonStatusCode.UNKNWON_CURRENCY,
											formattedErrorMessageMap);
		}
	}

}
