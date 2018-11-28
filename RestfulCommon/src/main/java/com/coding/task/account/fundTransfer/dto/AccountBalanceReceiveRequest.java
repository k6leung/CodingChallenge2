package com.coding.task.account.fundTransfer.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//marker subclass
public class AccountBalanceReceiveRequest extends AbstractAccountBalanceTransferRequest 
											implements Serializable {

	private static final long serialVersionUID = 4977791560593657615L;

	public AccountBalanceReceiveRequest(String accountNumber, 
											String currency, 
											BigDecimal value,
											String sequenceNumber,
											LocalDateTime createdTime) {
		super(accountNumber, currency, value, sequenceNumber, createdTime);
	}
	
	public AccountBalanceReceiveRequest() {
		super();
	}

}
