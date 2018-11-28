package com.coding.task.accountBalanceTransferRequest.validator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.coding.task.account.fundTransfer.dto.AbstractAccountBalanceTransferRequest;
import com.coding.task.accountNumber.validator.AccountNumberValidator;
import com.coding.task.currency.validator.CurrencyValidator;
import com.coding.task.rest.errorCode.CommonStatusCode;
import com.coding.task.rest.exception.HttpStatusException;
import com.coding.task.transferBalance.validator.TransferBalanceValidator;

@Component
public class AccountBalanceTransferRequestValidatorImpl implements AccountBalanceTransferRequestValidator {
	
	@Autowired
	protected AccountNumberValidator accountNumberValidator;
	
	@Autowired
	protected CurrencyValidator currencyValidator;
	
	@Autowired
	protected TransferBalanceValidator transferBalanceValidator;
	
	@Autowired
	protected AccountBalanceTransferRequestErrorMessage errorMessages;
	
	public AccountBalanceTransferRequestValidatorImpl() {
		super();
	}

	public void validate(AbstractAccountBalanceTransferRequest request) {
		if(request == null) {
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											CommonStatusCode.EMPTY_INVALID_TRANSFER_REQUEST,
											this.errorMessages.getEmptyOrInvalidRequestErrorMessage());
		}
		
		String accountNumber = request.getAccountNumber();
		String currency = request.getCurrency();
		BigDecimal value = request.getValue();
		
		this.accountNumberValidator.validate(accountNumber);
		this.currencyValidator.validate(currency);
		this.transferBalanceValidator.validate(value);
	}
	
}
