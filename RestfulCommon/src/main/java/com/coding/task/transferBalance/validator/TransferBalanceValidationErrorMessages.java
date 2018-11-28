package com.coding.task.transferBalance.validator;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.coding.task.messages.loading.ClasspathResourceJsonMessageLoader;

@Component
public class TransferBalanceValidationErrorMessages {

	private static final String errorMessageFolder = "classpath:/errorMessages/transferBalanceValidation/";
	
	private static final String emptyOrInvalidTransferAmountJsonFile = "emptyOrInvalidTransferAmount.json";
	
	private static final String negativeTransferAmountJsonFile = "negativeTransferAmount.json";
	
	protected Map<String, String> emptyOrInvalidTransferAmountErrorMessage;
	
	protected Map<String, String> negativeTransferAmountErrorMessage;
	
	public TransferBalanceValidationErrorMessages() {
		super();
	}
	
	@PostConstruct
	public void loadErrorMessages() throws IOException {
		this.emptyOrInvalidTransferAmountErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(TransferBalanceValidationErrorMessages.errorMessageFolder +
																																		TransferBalanceValidationErrorMessages.emptyOrInvalidTransferAmountJsonFile);

		this.negativeTransferAmountErrorMessage = ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation(TransferBalanceValidationErrorMessages.errorMessageFolder +
																																TransferBalanceValidationErrorMessages.negativeTransferAmountJsonFile);
	}

	public Map<String, String> getEmptyOrInvalidTransferAmountErrorMessage() {
		return emptyOrInvalidTransferAmountErrorMessage;
	}

	public Map<String, String> getNegativeTransferAmountErrorMessage() {
		return negativeTransferAmountErrorMessage;
	}
	
}
