package com.coding.task.records.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("DebitRecords")
public class DebitRecord implements Serializable {

	private static final long serialVersionUID = -6594912602016872685L;
	
	@Id
	protected String id;
	
	@Indexed
	protected String accountNumber; //assuming account number is always numeric
	
	protected String currency;
	
	protected BigDecimal value;
	
	protected LocalDateTime createdTime;
	
	public DebitRecord() {
		super();
	}

	public DebitRecord(String id, String accountNumber, String currency, BigDecimal value, LocalDateTime createdTime) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.currency = currency;
		this.value = value;
		this.createdTime = createdTime;
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

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

}
