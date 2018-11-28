package com.coding.task.transaction.restClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.coding.task.account.fundTransfer.dto.AccountBalanceReceiveRequest;
import com.coding.task.account.fundTransfer.dto.AccountBalanceWithdrawRequest;
import com.coding.task.account.model.MultiCurrencyAccount;
import com.coding.task.restUriBuilder.RestUriBuilder;

@Component
public class AccountServiceRestClientImpl implements AccountServiceRestClient {

	@Value("${account.service.host}")
	protected String accountServiceHost;
	
	@Value("${account.service.enquiry.uri.path}")
	protected String accountServiceEnquiryUriPath;
	
	@Value("${account.service.debit.uri.path}")
	protected String accountServiceDebitUriPath;
	
	@Value("${account.service.credit.uri.path}")
	protected String accountServiceCreditUriPath;
	
	@Autowired
	protected RestTemplate restTemplate;
	
	public AccountServiceRestClientImpl() {
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

	@Override
	public void debit(AccountBalanceWithdrawRequest request) {
		this.restTemplate.patchForObject(RestUriBuilder.formUri(this.accountServiceHost, this.accountServiceDebitUriPath), 
											request, Void.class);

	}

	@Override
	public void credit(AccountBalanceReceiveRequest request) {
		this.restTemplate.patchForObject(RestUriBuilder.formUri(this.accountServiceHost, this.accountServiceCreditUriPath), 
											request, 
											Void.class);
	}

}
