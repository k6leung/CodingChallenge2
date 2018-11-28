package com.coding.task.records.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.coding.task.account.fundTransfer.dto.TransactionRecordCreationRequest;
import com.coding.task.records.model.TransactionRecord;
import com.coding.task.records.service.TransactionRecordService;
import com.coding.task.records.validator.RecordValidator;

//NOTE: exceptions are handled by com.coding.task.rest.exception.controllerAdvice.CommonExceptionHandlerControllerAdvice from RestfulCommon project.

@RestController
@RequestMapping("/api")
public class TransactionRecordRestController {
	
	@Autowired
	protected TransactionRecordService transactionRecordService;
	
	@Autowired
	protected RecordValidator transactionRecordRequestValidator;
	
	public TransactionRecordRestController() {
		super();
	}
	
	@PostMapping(value="/transactionRecord",
					produces="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public TransactionRecord createTransactionRecord(@RequestBody TransactionRecordCreationRequest request) {
		this.transactionRecordRequestValidator.validateTransactionRecordCreationRequest(request);
		
		TransactionRecord record = new TransactionRecord(null,
															request.getDebitRecordId(),
															request.getCreditRecordId(),
															request.getCreatedTime());
		
		TransactionRecord result = this.transactionRecordService.createTransactionRecord(record);
		
		return result;
	}

}
