package com.coding.task.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

//a very simply model or a multi-currency account, assuming no security, no account owner at this moment
//use redis to simulate a NoSQL DB for flexibility and ease
@RedisHash("MultiCurrencyAccounts")
public class MultiCurrencyAccount implements Serializable {

	private static final long serialVersionUID = 2031169036311998738L;

	@Id
	protected String id;
	
	@Indexed
	protected String accountNumber; //assuming account number is always numeric
	
	protected Map<String, BigDecimal> currencyBalances;
	
	public MultiCurrencyAccount() {
		super();
		
		this.currencyBalances = new HashMap<String, BigDecimal>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
