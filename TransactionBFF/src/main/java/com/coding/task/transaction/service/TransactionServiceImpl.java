package com.coding.task.transaction.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import com.coding.task.account.fundTransfer.dto.AccountBalanceReceiveRequest;
import com.coding.task.account.fundTransfer.dto.AccountBalanceWithdrawRequest;
import com.coding.task.account.fundTransfer.dto.TransactionRecordCreationRequest;
import com.coding.task.account.model.MultiCurrencyAccount;
import com.coding.task.log.jsonPrinter.ObjectJsonPrinter;
import com.coding.task.records.model.CreditRecord;
import com.coding.task.records.model.DebitRecord;
import com.coding.task.records.model.TransactionRecord;
import com.coding.task.rest.exception.HttpStatusException;
import com.coding.task.transaction.errorCode.TransactionErrorCodes;
import com.coding.task.transaction.model.TransactionRequest;
import com.coding.task.transaction.model.TransactionResponse;
import com.coding.task.transaction.restClient.AccountServiceRestClient;
import com.coding.task.transaction.restClient.TransactionRecordServiceRestClient;
import com.coding.task.transaction.transaction.validator.TransactionRequestErrorMessages;
import com.coding.task.transaction.transaction.validator.TransactionRequestValidator;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	private static Logger log = Logger.getLogger(TransactionServiceImpl.class);
	
	@Value("${spring.application.name}")
	protected String applicationName;
	
	@Autowired
	protected AccountServiceRestClient accountServiceRestClient;
	
	@Autowired
	protected TransactionRecordServiceRestClient transactionRecordServiceRestClient;
	
	@Autowired
	protected TransactionRequestValidator transactionRequestValidator;
	
	@Autowired
	protected TransactionRequestErrorMessages errorMessages;
	
	public TransactionServiceImpl() {
		super();
	}

	//generate a simple iso datetime string as the sequence number....
	//assuming single node... if multiple instances of this service, this will also need something else...
	protected String generateSimpleSequenceNumberString() {
		LocalDateTime now = LocalDateTime.now();
		
		String result = this.applicationName + "-" + DateTimeFormatter.ISO_DATE_TIME.format(now);
		
		return result;
	}
	
	protected DebitRecord createDebitRecord(AccountBalanceWithdrawRequest debitRequest) {
		log.info("TransactionServiceImpl.createDebitRecord() - attempt create debit record: " + ObjectJsonPrinter.printObjectAsJson(debitRequest));
		DebitRecord result = null;
		
		try {
			result = this.transactionRecordServiceRestClient.createDebitRecord(debitRequest);
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			log.error("ERROR - PLEASE CREATE DEBIT RECORD WITH FOLLOWING INFORMATION: " + ObjectJsonPrinter.printObjectAsJson(debitRequest));
		}
		
		return result;
	}
	
	protected CreditRecord createCreditRecord(AccountBalanceReceiveRequest creditRequest) {
		log.info("TransactionServiceImpl.createCreditRecord() - attempt create credit record: " + ObjectJsonPrinter.printObjectAsJson(creditRequest));
		CreditRecord result = null;
		
		try {
			this.transactionRecordServiceRestClient.createCreditRecord(creditRequest);
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			log.error("ERROR - PLEASE CREATE CREDIT RECORD WITH FOLLOWING INFORMATION: " + ObjectJsonPrinter.printObjectAsJson(creditRequest));
		}
		
		return result;
	}
	
	protected TransactionRecord createTransactionRecord(TransactionRecordCreationRequest transactionRequest) {
		log.info("TransactionServiceImpl.createTransactionRecord() - attempt create transaction record: " + ObjectJsonPrinter.printObjectAsJson(transactionRequest));
		TransactionRecord result = null;
		
		try {
			this.transactionRecordServiceRestClient.createTransactionRecord(transactionRequest);
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			log.error("ERROR - PLEASE CREATE TRANSACTION RECORD WITH FOLLOWING INFORMATION: "+ ObjectJsonPrinter.printObjectAsJson(transactionRequest));
		}
		
		return result;
	}
	
	protected MultiCurrencyAccount attemptEnquireAccount(String accountNumber, String sequenceNumberString) {
		MultiCurrencyAccount result = null;
		
		try {
			result = this.accountServiceRestClient.findAccountByAccountNumber(accountNumber);
		} catch (RuntimeException re) {
			log.error("TransactionServiceImpl.attemptEnquireAccount() - sequence: " + sequenceNumberString + " " + re.getMessage(), re);
		}
		
		return result;
	}
	
	protected void attemptRollback(AccountBalanceReceiveRequest rollbackRequest, String sequenceNumberString) {
		try {
			this.accountServiceRestClient.credit(rollbackRequest);
			
			LocalDateTime rollbackDateTime = LocalDateTime.now();
			rollbackRequest.setCreatedTime(rollbackDateTime);
			log.info("TransactionServiceImpl.attemptRollback() - sequence: " + sequenceNumberString + " rollback attempt successful.");
			
			this.createCreditRecord(rollbackRequest);
		} catch (RuntimeException rollbackRuntimeException) {
			//failed rollback, report fail and ask customer to approach tech support with transaction details for manual rollback
			log.error("TransactionServiceImpl.attemptRollback() - sequence: " + sequenceNumberString + " " + rollbackRuntimeException.getMessage(), rollbackRuntimeException);
			log.error("ERROR - PLEASE ROLLBACK TRANSACTION WITH THE FOLLOWING INFORMATION: " + ObjectJsonPrinter.printObjectAsJson(rollbackRequest));
			
			HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
			if(rollbackRuntimeException instanceof RestClientResponseException) {
				statusCode = HttpStatus.resolve(((RestClientResponseException)rollbackRuntimeException).getRawStatusCode());
			}
			
			throw new HttpStatusException(statusCode,
											TransactionErrorCodes.UNRECOVERED_TRANSACTION,
											this.errorMessages.getFailedTransactionUnrecoveredErrorMessage());
		}
	}
	
	protected CreditRecord attemptCredit(String debitAccountNumber,
											String creditAccountNumber,
											String currency,
											BigDecimal value,
											String sequenceNumberString) {
		log.info("TransactionServiceImpl.attemptCredit() - sequence: " + sequenceNumberString + " attempt credit.");
		CreditRecord result = null;
		
		AccountBalanceReceiveRequest creditRequest = new AccountBalanceReceiveRequest(creditAccountNumber,
																						currency,
																						value,
																						sequenceNumberString,
																						null);

		try {
			this.accountServiceRestClient.credit(creditRequest);
		} catch (RuntimeException re) {
			log.error("TransactionServiceImpl.attemptCredit() - sequence: " + sequenceNumberString + " " + re.getMessage(), re);
			log.info("TransactionServiceImpl.attemptCredit() - sequence: " + sequenceNumberString + " attempt rollback.");
			AccountBalanceReceiveRequest rollbackRequest = new AccountBalanceReceiveRequest(debitAccountNumber,
																								currency,
																								value,
																								sequenceNumberString,
																								null);
			this.attemptRollback(rollbackRequest, sequenceNumberString);
			//successful rollback, only report transaction fail
			
			HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
			if(re instanceof RestClientResponseException) {
				statusCode = HttpStatus.resolve(((RestClientResponseException)re).getRawStatusCode());
			}
			
			
			throw new HttpStatusException(statusCode,
											TransactionErrorCodes.RECOVERED_TRANSACTION,
											this.errorMessages.getFailedTransactionRecoveredErrorMessage());
		}
		
		LocalDateTime creditDateTime = LocalDateTime.now();
		creditRequest.setCreatedTime(creditDateTime);
		log.info("TransactionServiceImpl.createTransaction() - sequence: " + sequenceNumberString + " credit attempt successful.");
		
		result = this.createCreditRecord(creditRequest);
		
		return result;
	}
	
	//first debit
		//if successful
			//create debit record (if fail, fail silently and continue the transaction, log it and generate alert; alerting is not in scope)
		//if debit fail
			//fail the entire process.
		//then credit
		//if credit successful, 
			//create credit record (if fail, fail silently and continue the transaction , log it and generate alert; alerting is not in scope)
			//create transaction record (if fail, fail silently, log it and generate alert; alerting is not in scope)
		//if credit fail, try credit the amount back to debit account as rollback
			//if credit to debit account successful, create credit record to debit account (if fail, fail silently , log it and generate alert; alerting is not in scope)
			//if credit to debit account also fail, log and alert(alerting is not in scope)
			//fail the process
	@Override
	public TransactionResponse createTransaction(TransactionRequest request) {
		String sequenceNumberString = this.generateSimpleSequenceNumberString();
		log.info("TransactionServiceImpl.createTransaction() - sequence: " + sequenceNumberString + " request: " + ObjectJsonPrinter.printObjectAsJson(request));
		
		this.transactionRequestValidator.validate(request);
		
		TransactionResponse result = null;
		
		String debitAccountNumber = request.getDebitAccountNumber();	
		String creditAccountNumber = request.getCreditAccountNumber();
		String currency = request.getCurrency();
		BigDecimal value = request.getValue();
		
		log.info("TransactionServiceImpl.createTransaction() - sequence: " + sequenceNumberString + " attenpt debit.");
		AccountBalanceWithdrawRequest debitRequest = new AccountBalanceWithdrawRequest(debitAccountNumber,
																						currency,
																						value,
																						sequenceNumberString,
																						null);
			
		this.accountServiceRestClient.debit(debitRequest);
		
		LocalDateTime debitDateTime = LocalDateTime.now();
		debitRequest.setCreatedTime(debitDateTime);
		log.info("TransactionServiceImpl.createTransaction() - sequence: " + sequenceNumberString + " debit attempt successful.");
		
		//NOTE: since createDebitRecord() and createCreditRecord() can fail silently and return null, null check is needed when creating transaction record.
		DebitRecord debitRecord = this.createDebitRecord(debitRequest);
		
		CreditRecord creditRecord = this.attemptCredit(debitAccountNumber, 
														creditAccountNumber, 
														currency, 
														value, 
														sequenceNumberString);
		
		//mark transaction record
		LocalDateTime transactionDateTime = LocalDateTime.now();
		
		String debitRecordId = (debitRecord != null) ? debitRecord.getId() : null;
		String creditRecordId = (creditRecord != null) ? creditRecord.getId() : null;
		
		if((debitRecordId != null) && (creditRecordId != null)) { //if either one is null, no need to call, simply log
			TransactionRecordCreationRequest transactionRequest = new TransactionRecordCreationRequest(debitRecordId,
																										creditRecordId,
																										transactionDateTime);
		
			TransactionRecord transactionRecord = this.createTransactionRecord(transactionRequest);
		} else {
			log.error("ERROR - PLEASE DATA PATCH/CREATE TRANSACTION RECORDS BY FOLLOWING LOGS WITH SEQUENCE NUMBER: " + sequenceNumberString);
		}
		
		MultiCurrencyAccount debitAccount = this.attemptEnquireAccount(debitAccountNumber, sequenceNumberString);
		
		result = new TransactionResponse(request, debitAccount);
		
		return result;
	}

}
