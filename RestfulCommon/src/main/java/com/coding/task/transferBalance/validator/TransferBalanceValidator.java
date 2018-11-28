package com.coding.task.transferBalance.validator;

import java.math.BigDecimal;

public interface TransferBalanceValidator {

	public void validate(BigDecimal value);
}
