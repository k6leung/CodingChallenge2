package com.coding.task.transaction.restClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.coding.task.account.fundTransfer.dto.AccountBalanceReceiveRequest;
import com.coding.task.account.fundTransfer.dto.AccountBalanceWithdrawRequest;
import com.coding.task.account.fundTransfer.dto.TransactionRecordCreationRequest;
import com.coding.task.records.model.CreditRecord;
import com.coding.task.records.model.DebitRecord;
import com.coding.task.records.model.TransactionRecord;

@Component
public class TransactionRecordServiceRestClientImpl implements TransactionRecordServiceRestClient {

	@Value("${transaction.record.service.host}")
	protected String transactionRecordServiceHost;
	
	@Value("${transaction.record.service.debit.create.uri.path}")
	protected String transactionRecordServiceDebitCreateUriPath;
	
	@Value("${transaction.record.service.credit.create.uri.path}")
	protected String transactionRecordServiceCreditCreateUriPath;
	
	@Value("${transaction.record.service.transaction.create.uri.path}")
	protected String transactionRecordServiceTransactionCreateUriPath;
	
	@Autowired
	protected RestTemplate restTemplate;
	
	public TransactionRecordServiceRestClientImpl() {
		super();
	}
	
	@Override
	public DebitRecord createDebitRecord(AccountBalanceWithdrawRequest request) {
		DebitRecord result = this.restTemplate.postForObject(this.transactionRecordServiceHost + this.transactionRecordServiceDebitCreateUriPath, 
																request, 
																DebitRecord.class);
		
		return result;
	}

	@Override
	public CreditRecord createCreditRecord(AccountBalanceReceiveRequest request) {
		CreditRecord result = this.restTemplate.postForObject(this.transactionRecordServiceHost + this.transactionRecordServiceCreditCreateUriPath, 
																request, 
																CreditRecord.class);
		
		return result;
	}

	@Override
	public TransactionRecord createTransactionRecord(TransactionRecordCreationRequest request) {
		TransactionRecord result = this.restTemplate.postForObject(this.transactionRecordServiceHost + this.transactionRecordServiceTransactionCreateUriPath, 
																	request, 
																	TransactionRecord.class);
		
		return result;
	}

}
