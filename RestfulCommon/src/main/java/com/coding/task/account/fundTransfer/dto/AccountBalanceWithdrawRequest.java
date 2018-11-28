package com.coding.task.account.fundTransfer.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountBalanceWithdrawRequest extends AbstractAccountBalanceTransferRequest implements Serializable {

	private static final long serialVersionUID = 8130260123026431343L;

	public AccountBalanceWithdrawRequest(String accountNumber, 
											String currency, 
											BigDecimal value,
											String sequenceNumber,
											LocalDateTime createdTime) {
		super(accountNumber, currency, value, sequenceNumber, createdTime);
	}
	
	public AccountBalanceWithdrawRequest() {
		super();
	}
}
