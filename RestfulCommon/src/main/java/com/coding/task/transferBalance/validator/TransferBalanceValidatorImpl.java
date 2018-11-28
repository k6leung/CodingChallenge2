package com.coding.task.transferBalance.validator;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.coding.task.messages.format.ErrorMessageFormatter;
import com.coding.task.rest.errorCode.CommonStatusCode;
import com.coding.task.rest.exception.HttpStatusException;

@Component
public class TransferBalanceValidatorImpl implements TransferBalanceValidator {
	
	@Autowired
	protected TransferBalanceValidationErrorMessages errorMessages;
	
	public TransferBalanceValidatorImpl() {
		super();
	}
	
	public void validate(BigDecimal value) {
		if(value == null) {
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessages.getEmptyOrInvalidTransferAmountErrorMessage(), 
																											value);
			
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											CommonStatusCode.EMPTY_INVALID_CURRENCY,
											formattedErrorMessageMap);
		}
		
		if(value.compareTo(new BigDecimal(0)) < 0) {
			Map<String, String> formattedErrorMessageMap = ErrorMessageFormatter.formatErrorMessageMap(this.errorMessages.getNegativeTransferAmountErrorMessage(),
																											value);
			
			throw new HttpStatusException(HttpStatus.BAD_REQUEST,
											CommonStatusCode.NEGATIVE_TRANSFER_AMOUNT,
											formattedErrorMessageMap);
		}
	}

}
