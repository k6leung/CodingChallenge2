package com.coding.task.records.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.coding.task.records.model.CreditRecord;
import com.coding.task.records.model.DebitRecord;
import com.coding.task.records.model.TransactionRecord;
import com.coding.task.records.repository.CreditRecordRepository;
import com.coding.task.records.repository.DebitRecordRepository;
import com.coding.task.records.repository.TransactionRecordRepository;
import com.coding.task.records.validator.RecordValidator;

@Service
public class TransactionRecordServiceImpl implements TransactionRecordService {
	
	@Autowired
	protected DebitRecordRepository debitRecordRepository;
	
	@Autowired
	protected CreditRecordRepository creditRecordRepository;
	
	@Autowired
	protected TransactionRecordRepository transactionRecordRepository;
	
	@Autowired
	protected RecordValidator recordValidator;
	
	public TransactionRecordServiceImpl() {
		super();
	}

	@Override
	public TransactionRecord createTransactionRecord(TransactionRecord record) {
		this.recordValidator.validateTransactionRecordNotNull(record);
		
		String debitRecordId = record.getDebitRecordId();
		
		DebitRecord debitRecord = this.debitRecordRepository.findById(debitRecordId).orElse(null);
		this.recordValidator.validateDebitRecordNotNull(debitRecord, debitRecordId);
		
		String creditRecordId = record.getCreditRecordId();
		
		CreditRecord creditRecord = this.creditRecordRepository.findById(creditRecordId).orElse(null);
		this.recordValidator.validateCreditRecordNotNull(creditRecord, creditRecordId);
		
		TransactionRecord result = this.transactionRecordRepository.save(record);
		
		return result;
	}

}
