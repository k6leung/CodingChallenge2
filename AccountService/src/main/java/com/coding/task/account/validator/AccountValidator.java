package com.coding.task.account.validator;

import com.coding.task.account.model.MultiCurrencyAccount;

public interface AccountValidator {

	public void validate(MultiCurrencyAccount target);
}
