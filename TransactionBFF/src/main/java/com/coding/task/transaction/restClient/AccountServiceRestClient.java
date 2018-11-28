package com.coding.task.transaction.restClient;

import com.coding.task.account.fundTransfer.dto.AccountBalanceReceiveRequest;
import com.coding.task.account.fundTransfer.dto.AccountBalanceWithdrawRequest;
import com.coding.task.account.model.MultiCurrencyAccount;

public interface AccountServiceRestClient {

	public MultiCurrencyAccount findAccountByAccountNumber(String accountNumber);
	
	public void debit(AccountBalanceWithdrawRequest request);
	
	public void credit(AccountBalanceReceiveRequest request);
}
