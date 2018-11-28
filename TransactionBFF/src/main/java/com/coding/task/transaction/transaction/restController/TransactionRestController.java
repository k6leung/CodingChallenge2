package com.coding.task.transaction.transaction.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.coding.task.transaction.model.TransactionRequest;
import com.coding.task.transaction.model.TransactionResponse;
import com.coding.task.transaction.service.TransactionService;

@RestController
@RequestMapping("/api")
public class TransactionRestController {
	
	@Autowired
	protected TransactionService transactionService;
	
	public TransactionRestController() {
		super();
	}
	
	@PostMapping(value="/transaction",
					produces="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public TransactionResponse createTransaction(@RequestBody TransactionRequest request) {
		TransactionResponse result = this.transactionService.createTransaction(request);
		
		return result;
	}

}
