package com.coding.task.balanceEnquiry.restClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.coding.task.account.model.MultiCurrencyAccount;

@Component
public class AccountEnquiryRestClientImpl implements AccountEnquiryRestClient {

	@Value("${account.service.host}")
	protected String accountServiceHost;
	
	@Value("${account.service.enquiry.uri.path}")
	protected String accountServiceEnquiryUriPath;
	
	@Autowired
	protected RestTemplate restTemplate;
	
	public AccountEnquiryRestClientImpl() {
		super();
	}
	
	//account service will either return 404 (translate to RestClientResponseException) or the account
	//therefore, we can always expect result to be not null.
	//if exception is thrown, let the controller advice to handle (both system exception like service down/connection fail or application exception can be handled by the advice)
	@Override
	public MultiCurrencyAccount findAccountByAccountNumber(String accountNumber) {
		MultiCurrencyAccount result = this.restTemplate.getForObject(this.accountServiceHost + this.accountServiceEnquiryUriPath, 
																		MultiCurrencyAccount.class, 
																		accountNumber);
		
		return result;
	}

}
