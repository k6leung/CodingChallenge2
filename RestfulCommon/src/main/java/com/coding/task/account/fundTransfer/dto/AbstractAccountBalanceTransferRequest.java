package com.coding.task.account.fundTransfer.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class AbstractAccountBalanceTransferRequest implements Serializable {

	private static final long serialVersionUID = -8050888372049854578L;

	protected String accountNumber;
	
	protected String currency;
	
	protected BigDecimal value;
	
	protected String sequenceNumber;
	
	protected LocalDateTime createdTime;
	
	public AbstractAccountBalanceTransferRequest() {
		super();
	}

	public AbstractAccountBalanceTransferRequest(String accountNumber, 
													String currency, 
													BigDecimal value,
													String sequenceNumber,
													LocalDateTime createdTime) {
		super();
		this.accountNumber = accountNumber;
		this.currency = currency;
		this.value = value;
		this.sequenceNumber = sequenceNumber;
		this.createdTime = createdTime;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	
}
