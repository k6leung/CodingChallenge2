package com.coding.task.transaction.model;

import java.io.Serializable;

import com.coding.task.account.model.MultiCurrencyAccount;

public class TransactionResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected TransactionRequest transactionRequest;
	
	//transaction requester may not have rights to the credit account, so not including here
	protected MultiCurrencyAccount debitAccount;
	
	public TransactionResponse() {
		super();
	}

	public TransactionResponse(TransactionRequest transactionRequest, MultiCurrencyAccount debitAccount) {
		super();
		this.transactionRequest = transactionRequest;
		this.debitAccount = debitAccount;
	}

	public TransactionRequest getTransactionRequest() {
		return transactionRequest;
	}

	public void setTransactionRequest(TransactionRequest transactionRequest) {
		this.transactionRequest = transactionRequest;
	}

	public MultiCurrencyAccount getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(MultiCurrencyAccount debitAccount) {
		this.debitAccount = debitAccount;
	}
	
}
