package com.coding.task.account.validator;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.coding.task.account.errorCode.AccountServiceErrorCodes;
import com.coding.task.rest.exception.HttpStatusException;

@Component
public class AccountIdValidatorImpl implements AccountIdValidator {
	
	@Autowired
	protected AccountValidationErrorMessages errorMessages;
	
	public AccountIdValidatorImpl() {
		super();
	}

	@Override
	public void validate(String id) {
		if(id == null) {
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											AccountServiceErrorCodes.INVALID_ACCOUNT_ID,
											this.errorMessages.getEmptyInvalidAccountIdErrorMessage());
		}

		if(id.compareTo("") == 0) {
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											AccountServiceErrorCodes.INVALID_ACCOUNT_ID,
											this.errorMessages.getEmptyInvalidAccountIdErrorMessage());
		}
	}

}
