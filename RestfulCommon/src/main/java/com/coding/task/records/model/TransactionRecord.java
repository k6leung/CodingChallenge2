package com.coding.task.records.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("TransactionRecords")
public class TransactionRecord implements Serializable {

	private static final long serialVersionUID = -1433796828819287613L;
	
	@Id
	protected String id;
	
	protected String debitRecordId;
	
	protected String creditRecordId;
	
	protected LocalDateTime createdTime;
	
	public TransactionRecord() {
		super();
	}

	public TransactionRecord(String id, String debitRecordId, String creditRecordId, LocalDateTime createdTime) {
		super();
		this.id = id;
		this.debitRecordId = debitRecordId;
		this.creditRecordId = creditRecordId;
		this.createdTime = createdTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
