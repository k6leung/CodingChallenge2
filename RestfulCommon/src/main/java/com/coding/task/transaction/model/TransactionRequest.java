package com.coding.task.transaction.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRequest implements Serializable {

	private static final long serialVersionUID = 1244616483900935450L;
	
	protected String debitAccountNumber;
	
	protected String creditAccountNumber;
	
	protected String currency;
	
	protected BigDecimal value;

	public TransactionRequest() {
		super();
	}
	
	public TransactionRequest(String debitAccountNumber, String creditAccountNumber, String currency, BigDecimal value) {
		super();
		this.debitAccountNumber = debitAccountNumber;
		this.creditAccountNumber = creditAccountNumber;
		this.currency = currency;
		this.value = value;
	}

	public String getDebitAccountNumber() {
		return debitAccountNumber;
	}

	public void setDebitAccountNumber(String debitAccountNumber) {
		this.debitAccountNumber = debitAccountNumber;
	}

	public String getCreditAccountNumber() {
		return creditAccountNumber;
	}

	public void setCreditAccountNumber(String creditAccountNumber) {
		this.creditAccountNumber = creditAccountNumber;
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

}
