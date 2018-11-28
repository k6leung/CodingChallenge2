package com.coding.task.records.validator;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.coding.task.account.fundTransfer.dto.TransactionRecordCreationRequest;
import com.coding.task.log.jsonPrinter.ObjectJsonPrinter;
import com.coding.task.messages.format.ErrorMessageFormatter;
import com.coding.task.records.errorCode.RecordErrorCodes;
import com.coding.task.records.model.CreditRecord;
import com.coding.task.records.model.DebitRecord;
import com.coding.task.records.model.TransactionRecord;
import com.coding.task.rest.exception.HttpStatusException;

@Component
public class RecordValidator {
	
	@Autowired
	protected TransactionRecordErrorMessages errorMessages;
	
	public RecordValidator() {
		super();
	}
	
	public void validateDebitRecordNotNull(DebitRecord record, String recordId) {
		if(record == null) {
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessages.getDebitRecordNotFoundErrorMessage(),
																										recordId);
			
			throw new HttpStatusException(HttpStatus.NOT_FOUND,
											RecordErrorCodes.DEBIT_RECORD_NOT_FOUND,
											formattedErrorMessageMap);
		}
	}
	
	public void validateCreditRecordNotNull(CreditRecord record, String recordId) {
		if(record == null) {
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessages.getCreditRecordNotFoundErrorMessage(),
																											recordId);
			
			throw new HttpStatusException(HttpStatus.NOT_FOUND,
											RecordErrorCodes.CREDIT_RECORD_NOT_FOUND,
											formattedErrorMessageMap);
		}
	}
	
	public void validateTransactionRecordNotNull(TransactionRecord record) {
		if(record == null) {
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessages.getInvalidTransactionRecordErrorMessage(), 
																										ObjectJsonPrinter.printObjectAsJson(record));
			
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											RecordErrorCodes.INVALID_TRANSACTION_RECORD,
											formattedErrorMessageMap);
		}
	}
	
	public void validateTransactionRecordCreationRequest(TransactionRecordCreationRequest request) {
		if(request == null) {
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessages.getInvalidTransactionRecordErrorMessage(), 
																										ObjectJsonPrinter.printObjectAsJson(request));

			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											RecordErrorCodes.INVALID_TRANSACTION_RECORD,
											formattedErrorMessageMap);
		}
	}

}
