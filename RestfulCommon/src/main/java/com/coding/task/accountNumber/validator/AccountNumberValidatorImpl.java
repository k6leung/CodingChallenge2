package com.coding.task.accountNumber.validator;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.coding.task.messages.format.ErrorMessageFormatter;
import com.coding.task.rest.errorCode.CommonStatusCode;
import com.coding.task.rest.exception.HttpStatusException;

@Component
public class AccountNumberValidatorImpl implements AccountNumberValidator {
	
	@Autowired
	protected AccountNumberValidationErrorMessages errorMessage;
	
	public AccountNumberValidatorImpl() {
		super();
	}

	public void validate(String accountNumber) {
		if(accountNumber == null) {
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessage.getErrorMessage(), 
																										accountNumber);
			
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											CommonStatusCode.EMPTY_INVALID_ACCOUNT_NUMBER,
											formattedErrorMessageMap);
		}
		
		if(StringUtils.isEmpty(accountNumber)) {
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessage.getErrorMessage(), 
																										accountNumber);
			
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											CommonStatusCode.EMPTY_INVALID_ACCOUNT_NUMBER,
											formattedErrorMessageMap);
		}
		if(!accountNumber.matches("[0-9]+")) {
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessage.getErrorMessage(), 
					accountNumber);

			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											CommonStatusCode.EMPTY_INVALID_ACCOUNT_NUMBER,
											formattedErrorMessageMap);
		}
	}	
}
