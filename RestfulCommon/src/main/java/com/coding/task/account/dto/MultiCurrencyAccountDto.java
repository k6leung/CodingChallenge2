package com.coding.task.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class MultiCurrencyAccountDto implements Serializable {

	private static final long serialVersionUID = -2225390935564230463L;

	protected String accountNumber;
	
	protected Map<String, BigDecimal> currencyBalances;
	
	public MultiCurrencyAccountDto() {
		super();
	}

	public MultiCurrencyAccountDto(String accountNumber, Map<String, BigDecimal> currencyBalances) {
		super();
		this.accountNumber = accountNumber;
		this.currencyBalances = currencyBalances;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Map<String, BigDecimal> getCurrencyBalances() {
		return currencyBalances;
	}

	public void setCurrencyBalances(Map<String, BigDecimal> currencyBalances) {
		this.currencyBalances = currencyBalances;
	}
	
}
