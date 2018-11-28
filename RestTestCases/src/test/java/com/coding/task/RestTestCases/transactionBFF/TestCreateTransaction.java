package com.coding.task.RestTestCases.transactionBFF;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import com.coding.task.account.model.MultiCurrencyAccount;
import com.coding.task.transaction.model.TransactionRequest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class TestCreateTransaction {
	
	private MultiCurrencyAccount hkdDebitAccount;
	
	private MultiCurrencyAccount usdDebitAccount;
	
	private MultiCurrencyAccount hkdUsdDebitAccount;
	
	private MultiCurrencyAccount hkdCreditAccount;
	
	private MultiCurrencyAccount usdCreditAccount;
	
	private MultiCurrencyAccount hkdUsdCreditAccount;
	
	@Before
	public void setup() {
		RestAssured.baseURI = "http://accounts.local.pcfdev.io/api";
	    RestAssured.port = 80;
	    
	    this.hkdDebitAccount = new MultiCurrencyAccount();
	    this.hkdDebitAccount.setAccountNumber("00900");
	    Map<String, BigDecimal> debitCurrencyBalanceMap = new HashMap<String, BigDecimal>();
	    debitCurrencyBalanceMap.put("HKD", new BigDecimal(100));
		this.hkdDebitAccount.setCurrencyBalances(debitCurrencyBalanceMap);
		
		Response response = with().body(this.hkdDebitAccount)
									.contentType("application/json")
									.when()
									.request("POST", "/account");
		
		this.usdDebitAccount = new MultiCurrencyAccount();
		this.usdDebitAccount.setAccountNumber("00901");
		debitCurrencyBalanceMap = new HashMap<String, BigDecimal>();
	    debitCurrencyBalanceMap.put("USD", new BigDecimal(100));
	    this.usdDebitAccount.setCurrencyBalances(debitCurrencyBalanceMap);
	    
	    response = with().body(this.usdDebitAccount)
							.contentType("application/json")
							.when()
							.request("POST", "/account");
	    
	    this.hkdUsdDebitAccount = new MultiCurrencyAccount();
		this.hkdUsdDebitAccount.setAccountNumber("00902");
		debitCurrencyBalanceMap = new HashMap<String, BigDecimal>();
		debitCurrencyBalanceMap.put("HKD", new BigDecimal(100));
	    debitCurrencyBalanceMap.put("USD", new BigDecimal(100));
	    this.hkdUsdDebitAccount.setCurrencyBalances(debitCurrencyBalanceMap);
		
	    response = with().body(this.hkdUsdDebitAccount)
							.contentType("application/json")
							.when()
							.request("POST", "/account");
		
		this.hkdCreditAccount = new MultiCurrencyAccount();
		this.hkdCreditAccount.setAccountNumber("00903");
		Map<String, BigDecimal> creditCurrencyBalanceMap = new HashMap<String, BigDecimal>();
		creditCurrencyBalanceMap.put("HKD", new BigDecimal(100));
		this.hkdCreditAccount.setCurrencyBalances(creditCurrencyBalanceMap);
		
		response = with().body(this.hkdCreditAccount)
							.contentType("application/json")
							.when()
							.request("POST", "/account");
		
		this.usdCreditAccount = new MultiCurrencyAccount();
		this.usdCreditAccount.setAccountNumber("00904");
		creditCurrencyBalanceMap = new HashMap<String, BigDecimal>();
		creditCurrencyBalanceMap.put("USD", new BigDecimal(100));
		this.usdCreditAccount.setCurrencyBalances(creditCurrencyBalanceMap);
		
		response = with().body(this.usdCreditAccount)
							.contentType("application/json")
							.when()
							.request("POST", "/account");
		
		this.hkdUsdCreditAccount = new MultiCurrencyAccount();
		this.hkdUsdCreditAccount.setAccountNumber("00905");
		creditCurrencyBalanceMap = new HashMap<String, BigDecimal>();
		creditCurrencyBalanceMap.put("HKD", new BigDecimal(100));
		creditCurrencyBalanceMap.put("USD", new BigDecimal(100));
		this.hkdUsdCreditAccount.setCurrencyBalances(creditCurrencyBalanceMap);
		
		response = with().body(this.hkdUsdCreditAccount)
							.contentType("application/json")
							.when()
							.request("POST", "/account");
		
		RestAssured.baseURI = "http://transactions.local.pcfdev.io/api";
	}
	

	public void testTransaction(TransactionRequest request,
									int expectedStatusCode,
									Number expectedDebitBalanceAfterTransaction,
									Number expectedCreditBalanceAfterTransaction) {
		
		String debitAccountNumber = request.getDebitAccountNumber();
		String creditAccountNumber = request.getCreditAccountNumber();
		String currency = request.getCurrency();
		
		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("POST", "/transaction")
												.then()
												.statusCode(expectedStatusCode);
		
		RestAssured.baseURI = "http://accounts.local.pcfdev.io/api";
		
		response = when().get("/account/accountNumber/{accountNumber}", debitAccountNumber)
							.then();
		response.body("currencyBalances." + currency, equalTo(expectedDebitBalanceAfterTransaction));
		
		response = when().get("/account/accountNumber/{accountNumber}", creditAccountNumber)
							.then();
		response.body("currencyBalances." + currency, equalTo(expectedCreditBalanceAfterTransaction));	
		
		RestAssured.baseURI = "http://transactions.local.pcfdev.io/api";
	}
	
	@Test
	public void testValidHkdToHkdTransaction() {
		TransactionRequest request = new TransactionRequest("00900", 
																"00903",
																"HKD",
																new BigDecimal(10));
		
		this.testTransaction(request, 201, 90, 110);
	}
	
	@Test
	public void testValidUsdToUsdTransaction() {
		TransactionRequest request = new TransactionRequest("00901", 
																"00904",
																"USD",
																new BigDecimal(10));
		
		this.testTransaction(request, 201, 90, 110);
	}
	
	@Test
	public void testValidMultiToMultiTransaction() {
		TransactionRequest request = new TransactionRequest("00902", 
																"00905",
																"USD",
																new BigDecimal(10));
		
		this.testTransaction(request, 201, 90, 110);
		
		request = new TransactionRequest("00902", 
											"00905",
											"HKD",
											new BigDecimal(10));
		
		this.testTransaction(request, 201, 90, 110);
	}
	
	@Test
	public void testUnsupportedCurrencyTransaction() {
		//hkd to hkd account
		TransactionRequest request = new TransactionRequest("00900", 
																"00903",
																"PHP",
																new BigDecimal(10));
		
		this.testTransaction(request, 400, null, null);
		
		//hkd to usd account
		request = new TransactionRequest("00900",
											"00904",
											"HKD",
											new BigDecimal(10));
		
		this.testTransaction(request, 400, 100, null);
		
		//usd to hkd account
		request = new TransactionRequest("00901",
											"00903",
											"USD",
											new BigDecimal(10));
		
		this.testTransaction(request, 400, 100, null);
	}
	
	@Test
	public void testNegativeValueTransaction() {
		TransactionRequest request = new TransactionRequest("00900", 
																"00903",
																"HKD",
																new BigDecimal(-10));

		this.testTransaction(request, 400, 100, 100);
	}
	
	@Test
	public void testInsufficientFundTransaction() {
		TransactionRequest request = new TransactionRequest("00900", 
																"00903",
																"HKD",
																new BigDecimal(1000));

		this.testTransaction(request, 400, 100, 100);
	}
	

}
