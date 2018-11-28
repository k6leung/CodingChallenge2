package com.coding.task.transaction.restClient;

import com.coding.task.account.fundTransfer.dto.AccountBalanceReceiveRequest;
import com.coding.task.account.fundTransfer.dto.AccountBalanceWithdrawRequest;
import com.coding.task.account.fundTransfer.dto.TransactionRecordCreationRequest;
import com.coding.task.records.model.CreditRecord;
import com.coding.task.records.model.DebitRecord;
import com.coding.task.records.model.TransactionRecord;

public interface TransactionRecordServiceRestClient {

	public DebitRecord createDebitRecord(AccountBalanceWithdrawRequest request);
	
	public CreditRecord createCreditRecord(AccountBalanceReceiveRequest request);
	
	public TransactionRecord createTransactionRecord(TransactionRecordCreationRequest request);
}
