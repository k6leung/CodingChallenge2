package com.coding.task.account.validator;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.coding.task.account.errorCode.AccountServiceErrorCodes;
import com.coding.task.account.model.MultiCurrencyAccount;
import com.coding.task.accountNumber.validator.AccountNumberValidator;
import com.coding.task.currency.validator.CurrencyValidator;
import com.coding.task.rest.exception.HttpStatusException;
import com.coding.task.transferBalance.validator.TransferBalanceValidator;

@Component
public class AccountValidatorImpl implements AccountValidator {
	
	@Autowired
	protected AccountNumberValidator accountNumberValidator;
	
	@Autowired
	protected CurrencyValidator currencyValidator;
	
	@Autowired
	protected TransferBalanceValidator transferBalanceValidator;
	
	@Autowired
	protected AccountValidationErrorMessages accountValidationErrorMessages;
	
	public AccountValidatorImpl() {
		super();
	}

	@Override
	public void validate(MultiCurrencyAccount target) {
		if(target == null) {
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											AccountServiceErrorCodes.INVALID_ACCOUNT,
											this.accountValidationErrorMessages.getInvalidAccountRecordErrorMessage());
		}
		
		String accountNumber = target.getAccountNumber();
		Map<String, BigDecimal> currencyBalanceMap = target.getCurrencyBalances();
		
		this.accountNumberValidator.validate(accountNumber);
		
		if((currencyBalanceMap == null) || (currencyBalanceMap.size() == 0)) {
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											AccountServiceErrorCodes.INVALID_ACCOUNT,
											this.accountValidationErrorMessages.getInvalidAccountRecordErrorMessage());
		}
		
		//first hit will fail the entire process
		for(String key : currencyBalanceMap.keySet()) {
			BigDecimal balance = currencyBalanceMap.get(key);
			
			this.currencyValidator.validate(key);
			this.transferBalanceValidator.validate(balance);
		}

	}

}
