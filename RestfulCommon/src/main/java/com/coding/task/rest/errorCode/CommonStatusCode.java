package com.coding.task.rest.errorCode;

//should have a common table or collection to store the status codes... but since I am using redis to simulate a database for simplicity, this is used instead...
//bad about this approach: for each service, they may have their own class to store such codes...
public class CommonStatusCode {

	public static final String GENERIC_ERROR = "GE0000";
	
	public static final String EMPTY_INVALID_TRANSFER_REQUEST = "TF0001";
	
	public static final String EMPTY_INVALID_ACCOUNT_NUMBER = "AC0001";
	
	public static final String EMPTY_INVALID_CURRENCY = "CU0001";
	
	public static final String UNKNWON_CURRENCY = "CU0002";
	
	public static final String EMTPY_INVALID_TRANSFER_AMOUNT = "AM0001";

	public static final String NEGATIVE_TRANSFER_AMOUNT = "AM0002";
}
