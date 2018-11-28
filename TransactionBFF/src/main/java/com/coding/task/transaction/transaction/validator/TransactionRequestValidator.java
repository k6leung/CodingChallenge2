package com.coding.task.transaction.transaction.validator;

import com.coding.task.transaction.model.TransactionRequest;

public interface TransactionRequestValidator {

	public void validate(TransactionRequest request);
	
}
