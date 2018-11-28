package com.coding.task.balanceEnquiry.restClient;

import com.coding.task.account.model.MultiCurrencyAccount;

public interface AccountEnquiryRestClient {

	public MultiCurrencyAccount findAccountByAccountNumber(String accountNumber);
	
}
