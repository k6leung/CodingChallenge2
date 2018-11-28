package com.coding.task.account.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.coding.task.account.model.MultiCurrencyAccount;

public interface AccountRepository extends CrudRepository<MultiCurrencyAccount, String> {

	public Optional<MultiCurrencyAccount> findById(String id);
	
	public Optional<MultiCurrencyAccount> findByAccountNumber(String accountNumber);
}
