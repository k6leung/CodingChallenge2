package com.coding.task.account.service;

import com.coding.task.account.fundTransfer.dto.AccountBalanceReceiveRequest;
import com.coding.task.account.fundTransfer.dto.AccountBalanceWithdrawRequest;
import com.coding.task.account.model.MultiCurrencyAccount;

public interface AccountService {
	
	public MultiCurrencyAccount createOrUpdateNewAccount(MultiCurrencyAccount account);

	public MultiCurrencyAccount findAccountByAccountNumber(String accountNumber);
	
	public MultiCurrencyAccount findAccountById(String id);
	
	public void debit(AccountBalanceWithdrawRequest request);
	
	public void credit(AccountBalanceReceiveRequest request);
	
}
