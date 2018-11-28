package com.coding.task.account.validator;

import java.math.BigDecimal;

import com.coding.task.account.model.MultiCurrencyAccount;

public interface AccountDebitCreditValidator {

	public void validateAccountFound(MultiCurrencyAccount target,
										String accountNumber);
	
	public void validateAccountHasCurrency(MultiCurrencyAccount target, 
											String targetCurrency,
											String accountNumber);
	
	public void validateDebitAccountHasSufficientFund(MultiCurrencyAccount target, 
														String targetCurrency, 
														BigDecimal value,
														String accountNumber);
	
}
