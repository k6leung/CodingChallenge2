package com.coding.task.transaction.service;

import com.coding.task.transaction.model.TransactionRequest;
import com.coding.task.transaction.model.TransactionResponse;

public interface TransactionService {

	public TransactionResponse createTransaction(TransactionRequest request);
	
}
