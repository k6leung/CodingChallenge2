package com.coding.task.transaction.transaction.validator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.coding.task.accountNumber.validator.AccountNumberValidator;
import com.coding.task.currency.validator.CurrencyValidator;
import com.coding.task.rest.errorCode.CommonStatusCode;
import com.coding.task.rest.exception.HttpStatusException;
import com.coding.task.transaction.model.TransactionRequest;
import com.coding.task.transferBalance.validator.TransferBalanceValidator;

@Component
public class TransactionRequestValidatorImpl implements TransactionRequestValidator {
	
	@Autowired
	protected TransactionRequestErrorMessages errorMessages;
	
	@Autowired
	protected AccountNumberValidator accountNumberValidator;
	
	@Autowired
	protected CurrencyValidator currencyValidator;
	
	@Autowired
	protected TransferBalanceValidator transferBalanceValidator;
	
	public TransactionRequestValidatorImpl() {
		super();
	}

	@Override
	public void validate(TransactionRequest request) {
		if(request == null) {
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											CommonStatusCode.EMPTY_INVALID_TRANSFER_REQUEST,
											this.errorMessages.getEmptyInvalidTransactionRequestErrorMessage());
		}
		
		String debitAccountNumber = request.getDebitAccountNumber();
		this.accountNumberValidator.validate(debitAccountNumber);
		
		String creditAccountNumber = request.getCreditAccountNumber();
		this.accountNumberValidator.validate(creditAccountNumber);
		
		String currency = request.getCurrency();
		this.currencyValidator.validate(currency);
		
		BigDecimal value = request.getValue();
		this.transferBalanceValidator.validate(value);
	}

}
