package com.coding.task.account.validator;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.coding.task.account.errorCode.AccountServiceErrorCodes;
import com.coding.task.account.model.MultiCurrencyAccount;
import com.coding.task.messages.format.ErrorMessageFormatter;
import com.coding.task.rest.exception.HttpStatusException;

@Component
public class AccountDebitCreditValidatorImpl implements AccountDebitCreditValidator {
	
	@Autowired
	protected AccountValidationErrorMessages errorMessages;
	
	public AccountDebitCreditValidatorImpl() {
		super();
	}

	@Override
	public void validateAccountFound(MultiCurrencyAccount target, 
										String accountNumber) {
		if(target == null) { //account not found
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessages.getAccountNotFoundErrorMessage(), 
																										accountNumber);
			
			throw new HttpStatusException(HttpStatus.NOT_FOUND,
											AccountServiceErrorCodes.ACCOUNT_NOT_FOUND,
											formattedErrorMessageMap);
		}
	}

	@Override
	public void validateAccountHasCurrency(MultiCurrencyAccount target, 
											String targetCurrency, 
											String accountNumber) {
		//the targetCurrency should have been validated by AccountBalanceTransferRequestValidator
		Map<String, BigDecimal> targetCurrencyBalances = target.getCurrencyBalances();
		BigDecimal targetBalance = targetCurrencyBalances.get(targetCurrency);
		
		if((!targetCurrencyBalances.containsKey(targetCurrency)) || (targetBalance == null)) { //account has no target currency
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessages.getDebitUnsupportedCurrencyErrorMessage(),
																										targetCurrency,
																										accountNumber);
			
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											AccountServiceErrorCodes.UNSUPPORTED_CURRENCY,
											formattedErrorMessageMap);
		}
	}

	@Override
	public void validateDebitAccountHasSufficientFund(MultiCurrencyAccount target, 
														String targetCurrency,
														BigDecimal value,
														String accountNumber) {
		Map<String, BigDecimal> targetCurrencyBalances = target.getCurrencyBalances();
		BigDecimal targetBalance = targetCurrencyBalances.get(targetCurrency);
		
		if(targetBalance.compareTo(value) < 0) { //insufficient fund
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessages.getDebitInsufficientFundErrorMessage(),
																										accountNumber);
			
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											AccountServiceErrorCodes.INSUFFICIENT_FUND,
											formattedErrorMessageMap);
		}	
	}

}
