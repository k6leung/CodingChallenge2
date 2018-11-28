package com.coding.task.account.fundTransfer.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TransactionRecordCreationRequest implements Serializable {

	private static final long serialVersionUID = -695977183268065646L;
	
	protected String debitRecordId;
	
	protected String creditRecordId;
	
	protected LocalDateTime createdTime;
	
	public TransactionRecordCreationRequest() {
		super();
	}

	public TransactionRecordCreationRequest(String debitRecordId, String creditRecordId, LocalDateTime createdTime) {
		super();
		this.debitRecordId = debitRecordId;
		this.creditRecordId = creditRecordId;
		this.createdTime = createdTime;
	}

	public String getDebitRecordId() {
		return debitRecordId;
	}

	public void setDebitRecordId(String debitRecordId) {
		this.debitRecordId = debitRecordId;
	}

	public String getCreditRecordId() {
		return creditRecordId;
	}

	public void setCreditRecordId(String creditRecordId) {
		this.creditRecordId = creditRecordId;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	
}
