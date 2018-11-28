package com.coding.task.account.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.task.account.fundTransfer.dto.AccountBalanceReceiveRequest;
import com.coding.task.account.fundTransfer.dto.AccountBalanceWithdrawRequest;
import com.coding.task.account.model.MultiCurrencyAccount;
import com.coding.task.account.repository.AccountRepository;
import com.coding.task.account.validator.AccountDebitCreditValidator;
import com.coding.task.account.validator.AccountIdValidator;
import com.coding.task.account.validator.AccountValidator;
import com.coding.task.accountBalanceTransferRequest.validator.AccountBalanceTransferRequestValidator;
import com.coding.task.accountNumber.validator.AccountNumberValidator;
import com.coding.task.log.jsonPrinter.ObjectJsonPrinter;
import com.coding.task.messages.format.ObjectJsonConverter;

@Service
public class AccountServiceImpl implements AccountService {
	
	private static Logger log = Logger.getLogger(AccountServiceImpl.class);
	
	@Autowired
	protected AccountDebitCreditValidator accountDebitCreditValidator;
	
	@Autowired
	protected AccountNumberValidator accountNumberValidator;
	
	@Autowired
	protected AccountRepository accountRepository;
	
	@Autowired
	protected AccountBalanceTransferRequestValidator accountBalanceTransferRequestValidator;
	
	@Autowired
	protected AccountValidator accountValidator;
	
	@Autowired
	protected AccountIdValidator accountIdValidator;
	
	public AccountServiceImpl() {
		super();
	}
	
	@Override
	public MultiCurrencyAccount createOrUpdateNewAccount(MultiCurrencyAccount account) {
		log.info("AccountServiceImpl.createNewAccount() - account: " + ObjectJsonPrinter.printObjectAsJson(account));
		this.accountValidator.validate(account);
		MultiCurrencyAccount result = null;
		
		MultiCurrencyAccount existingRecord = this.findAccountByAccountNumber(account.getAccountNumber());
		
		if(existingRecord == null) {
			result = this.accountRepository.save(account);
		} else {
			//make account number unique
			Map<String, BigDecimal> newBalances = account.getCurrencyBalances();
			
			existingRecord.setCurrencyBalances(newBalances);
			result = this.accountRepository.save(existingRecord);
		}
		
		return result;
	}

	@Override
	public MultiCurrencyAccount findAccountByAccountNumber(String accountNumber) {
		log.info("AccountServiceImpl.findAccountByAccountNumber() - accountNumber: " + accountNumber);	
		this.accountNumberValidator.validate(accountNumber);
		
		MultiCurrencyAccount result = this.accountRepository.findByAccountNumber(accountNumber).orElse(null);
		
		return result;
	}
	
	@Override
	public MultiCurrencyAccount findAccountById(String id) {
		log.info("AccountServiceImpl.findAccountById() - id: " + id);
		this.accountIdValidator.validate(id);
		
		MultiCurrencyAccount result = this.accountRepository.findById(id).orElse(null);
		
		return result;
	}

	@Override
	public void debit(AccountBalanceWithdrawRequest request) {
		log.info("AccountServiceImpl.debit() - request: " + ObjectJsonConverter.convertObjectToJson(request));
		this.accountBalanceTransferRequestValidator.validate(request);
		
		String accountNumber = request.getAccountNumber();
		String currency = request.getCurrency();
		BigDecimal value = request.getValue();
		
		MultiCurrencyAccount target = this.findAccountByAccountNumber(accountNumber);
		
		this.accountDebitCreditValidator.validateAccountFound(target, 
																accountNumber);
		this.accountDebitCreditValidator.validateAccountHasCurrency(target, 
																		currency, 
																		accountNumber);
		this.accountDebitCreditValidator.validateDebitAccountHasSufficientFund(target, 
																				currency, 
																				value, 
																				accountNumber);
		
		//start updating
		Map<String, BigDecimal> targetCurrencyBalances = target.getCurrencyBalances();
		BigDecimal targetBalance = targetCurrencyBalances.get(currency);
		
		targetCurrencyBalances.put(currency, targetBalance.subtract(value));
		this.accountRepository.save(target);
	}

	@Override
	public void credit(AccountBalanceReceiveRequest request) {
		//exact opposite of previous method, only reverse the operation and less validation
		log.info("AccountServiceImpl.credit() - request: " + ObjectJsonConverter.convertObjectToJson(request));
		this.accountBalanceTransferRequestValidator.validate(request);
		
		String accountNumber = request.getAccountNumber();
		String currency = request.getCurrency();
		BigDecimal value = request.getValue();
		
		MultiCurrencyAccount target = this.findAccountByAccountNumber(accountNumber);
		
		this.accountDebitCreditValidator.validateAccountFound(target, 
																accountNumber);
		this.accountDebitCreditValidator.validateAccountHasCurrency(target, 
																		currency, 
																		accountNumber);
		
		//start updating
		Map<String, BigDecimal> targetCurrencyBalances = target.getCurrencyBalances();
		BigDecimal targetBalance = targetCurrencyBalances.get(currency);
		
		targetCurrencyBalances.put(currency, targetBalance.add(value));
		this.accountRepository.save(target);
	}

}
