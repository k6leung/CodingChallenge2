package com.coding.task.accountBalanceTransferRequest.validator;

import com.coding.task.account.fundTransfer.dto.AbstractAccountBalanceTransferRequest;

public interface AccountBalanceTransferRequestValidator {

	public void validate(AbstractAccountBalanceTransferRequest request);
	
}
