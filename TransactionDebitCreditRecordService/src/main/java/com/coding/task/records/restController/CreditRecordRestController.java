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

import com.coding.task.account.fundTransfer.dto.AccountBalanceReceiveRequest;
import com.coding.task.accountBalanceTransferRequest.validator.AccountBalanceTransferRequestValidator;
import com.coding.task.records.model.CreditRecord;
import com.coding.task.records.repository.CreditRecordRepository;
import com.coding.task.records.validator.RecordValidator;

//NOTE: exceptions are handled by com.coding.task.rest.exception.controllerAdvice.CommonExceptionHandlerControllerAdvice from RestfulCommon project.

@RestController
@RequestMapping("/api")
public class CreditRecordRestController {
	
	@Autowired
	protected CreditRecordRepository creditRecordRepository;
	
	@Autowired
	protected RecordValidator recordValidator;
	
	@Autowired
	protected AccountBalanceTransferRequestValidator creditRequestValidator;
	
	public CreditRecordRestController() {
		super();
	}
	
	@PostMapping(value="/creditRecord",
					produces="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public CreditRecord createCreditRecord(@RequestBody AccountBalanceReceiveRequest request) {
		this.creditRequestValidator.validate(request);
		
		CreditRecord record = new CreditRecord(null,
												request.getAccountNumber(),
												request.getCurrency(),
												request.getValue(),
												request.getCreatedTime());
		
		CreditRecord result = this.creditRecordRepository.save(record);
		
		return result;
	}
	
	@GetMapping(value="/creditRecord/{id}",
					produces="application/json")
	public CreditRecord findCreditRecordById(@PathVariable("id") String id) {
		CreditRecord result = this.creditRecordRepository.findById(id).orElse(null);
		
		this.recordValidator.validateCreditRecordNotNull(result, id);
		
		return result;
	}

}
