package com.coding.task.balanceEnquiry.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.task.account.dto.MultiCurrencyAccountDto;
import com.coding.task.account.model.MultiCurrencyAccount;
import com.coding.task.balanceEnquiry.restClient.AccountEnquiryRestClient;

@RestController
@RequestMapping("/api")
public class BalanceEnquiryRestController {
	
	@Autowired
	protected AccountEnquiryRestClient restClient;
	
	public BalanceEnquiryRestController() {
		super();
	}
	
	@GetMapping(value="/balance/{accountNumber}",
					produces="application/json")
	public MultiCurrencyAccountDto findAccountByAccountNumber(@PathVariable("accountNumber")String accountNumber) {
		MultiCurrencyAccount account = this.restClient.findAccountByAccountNumber(accountNumber);
		
		MultiCurrencyAccountDto result = new MultiCurrencyAccountDto(account.getAccountNumber(),
																		account.getCurrencyBalances());
		
		return result;
	}

}
