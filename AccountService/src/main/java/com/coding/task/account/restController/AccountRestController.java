package com.coding.task.account.restController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.coding.task.account.errorCode.AccountServiceErrorCodes;
import com.coding.task.account.fundTransfer.dto.AccountBalanceReceiveRequest;
import com.coding.task.account.fundTransfer.dto.AccountBalanceWithdrawRequest;
import com.coding.task.account.model.MultiCurrencyAccount;
import com.coding.task.account.service.AccountService;
import com.coding.task.account.validator.AccountValidationErrorMessages;
import com.coding.task.messages.format.ErrorMessageFormatter;
import com.coding.task.rest.exception.HttpStatusException;

//NOTE: exceptions are handled by com.coding.task.rest.exception.controllerAdvice.CommonExceptionHandlerControllerAdvice from RestfulCommon project.

@RestController
@RequestMapping("/api")
public class AccountRestController {
	
	@Autowired
	protected AccountService accountService;
	
	@Autowired
	protected AccountValidationErrorMessages errorMessages;
	
	public AccountRestController() {
		super();
	}

	@PostMapping(value="/account",
					produces="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public MultiCurrencyAccount createOrUpdateAccount(@RequestBody MultiCurrencyAccount account) {
		MultiCurrencyAccount result = this.accountService.createOrUpdateNewAccount(account);
		
		return result;
	}
	
	@GetMapping(value="/account/{id}",
					produces="application/json")
	public MultiCurrencyAccount findAccountById(@PathVariable("id")String id) {
		MultiCurrencyAccount result = this.accountService.findAccountById(id);
		
		if(result == null) {
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessages.getAccountIdNotFoundErrorMessage(),
																										id);
			
			throw new HttpStatusException(HttpStatus.NOT_FOUND,
											AccountServiceErrorCodes.ACCOUNT_NOT_FOUND,
											formattedErrorMessageMap);
		}
		
		return result;
	}
	
	@GetMapping(value="/account/accountNumber/{accountNumber}",
					produces="application/json")
	public MultiCurrencyAccount findAccountByAccountNumber(@PathVariable("accountNumber")String accountNumber) {
		MultiCurrencyAccount result = this.accountService.findAccountByAccountNumber(accountNumber);
		
		if(result == null) {
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessages.getAccountNotFoundErrorMessage(),
																										accountNumber);
			
			throw new HttpStatusException(HttpStatus.NOT_FOUND,
											AccountServiceErrorCodes.ACCOUNT_NOT_FOUND,
											formattedErrorMessageMap);
		}
		
		return result;
	}
	
	@PatchMapping(value="/account/debit")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void debit(@RequestBody AccountBalanceWithdrawRequest request) {
		this.accountService.debit(request);
	}
	
	@PatchMapping(value="/account/credit")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void credit(@RequestBody AccountBalanceReceiveRequest request) {
		this.accountService.credit(request);
	}
}
