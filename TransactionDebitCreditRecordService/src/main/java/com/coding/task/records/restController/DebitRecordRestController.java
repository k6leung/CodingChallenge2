package com.coding.task.records.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.coding.task.account.fundTransfer.dto.AccountBalanceWithdrawRequest;
import com.coding.task.accountBalanceTransferRequest.validator.AccountBalanceTransferRequestValidator;
import com.coding.task.records.model.DebitRecord;
import com.coding.task.records.repository.DebitRecordRepository;
import com.coding.task.records.validator.RecordValidator;

//NOTE: exceptions are handled by com.coding.task.rest.exception.controllerAdvice.CommonExceptionHandlerControllerAdvice from RestfulCommon project.

@RestController
@RequestMapping("/api")
public class DebitRecordRestController {
	
	@Autowired
	protected DebitRecordRepository debitRecordRepository;
	
	@Autowired
	protected RecordValidator recordValidator;
	
	@Autowired
	protected AccountBalanceTransferRequestValidator debitRequestValidator;
	
	public DebitRecordRestController() {
		super();
	}
	
	@PostMapping(value="/debitRecord",
					produces="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public DebitRecord createDebitRecord(@RequestBody AccountBalanceWithdrawRequest request) {
		this.debitRequestValidator.validate(request);
		
		DebitRecord record = new DebitRecord(null,
												request.getAccountNumber(),
												request.getCurrency(),
												request.getValue(),
												request.getCreatedTime());
		
		DebitRecord result = this.debitRecordRepository.save(record);
		
		return result;
	}
	
	@GetMapping(value="/debitRecord/{id}",
					produces="application/json")
	public DebitRecord findDebitRecordById(@PathVariable("id") String id) {
		DebitRecord result = this.debitRecordRepository.findById(id).orElse(null);
		
		this.recordValidator.validateDebitRecordNotNull(result, id);
		
		return result;
	}

}
