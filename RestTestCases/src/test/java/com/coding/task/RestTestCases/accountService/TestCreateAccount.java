package com.coding.task.RestTestCases.accountService;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.coding.task.account.model.MultiCurrencyAccount;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class TestCreateAccount {
	
	@Before
	public void setup() {
	    RestAssured.baseURI = "http://accounts.local.pcfdev.io/api";
	    RestAssured.port = 80;
	}

	@Test
	public void testCreateHkd() {
		MultiCurrencyAccount account = new MultiCurrencyAccount();
		
		account.setAccountNumber("00100");
		Map<String, BigDecimal> currencyBalanceMap = new HashMap<String, BigDecimal>();
		currencyBalanceMap.put("HKD", new BigDecimal(100));
		account.setCurrencyBalances(currencyBalanceMap);
		
		ValidatableResponse response = with().body(account)
												.contentType("application/json")
								  				.when()
								  				.request("POST", "/account")
								  				.then();
		
		response.body("accountNumber", equalTo("00100"));
		response.body("currencyBalances.HKD", equalTo(100)); //json uses number instead of BigDecimal...
	}
	
	@Test
	public void testCreateUsd() {
		MultiCurrencyAccount account = new MultiCurrencyAccount();
		
		account.setAccountNumber("00101");
		Map<String, BigDecimal> currencyBalanceMap = new HashMap<String, BigDecimal>();
		currencyBalanceMap.put("USD", new BigDecimal(100));
		account.setCurrencyBalances(currencyBalanceMap);
		
		ValidatableResponse response = with().body(account)
												.contentType("application/json")
								  				.when()
								  				.request("POST", "/account")
								  				.then();
		
		response.body("accountNumber", equalTo("00101"));
		response.body("currencyBalances.USD", equalTo(100));
	}
	
	@Test
	public void testUnsupportedCurrency() {
		MultiCurrencyAccount account = new MultiCurrencyAccount();
		
		account.setAccountNumber("00102");
		Map<String, BigDecimal> currencyBalanceMap = new HashMap<String, BigDecimal>();
		currencyBalanceMap.put("PHP", new BigDecimal(100));
		account.setCurrencyBalances(currencyBalanceMap);
		
		ValidatableResponse response = with().body(account)
												.contentType("application/json")
								  				.when()
								  				.request("POST", "/account")
								  				.then()
								  				.statusCode(400);
		
	}
	
	@Test
	public void testNegativeBalance() {
		MultiCurrencyAccount account = new MultiCurrencyAccount();
		
		account.setAccountNumber("00103");
		Map<String, BigDecimal> currencyBalanceMap = new HashMap<String, BigDecimal>();
		currencyBalanceMap.put("HKD", new BigDecimal(-100));
		account.setCurrencyBalances(currencyBalanceMap);
		
		ValidatableResponse response = with().body(account)
												.contentType("application/json")
								  				.when()
								  				.request("POST", "/account")
								  				.then()
								  				.statusCode(400);
	}

	@Test
	public void testCreateEmptyCurrencyBalanceMap() {
		MultiCurrencyAccount account = new MultiCurrencyAccount();
		
		account.setAccountNumber("00104");
		account.setCurrencyBalances(null);
		
		ValidatableResponse response = with().body(account)
												.contentType("application/json")
												.when()
												.request("POST", "/account")
												.then()
												.statusCode(400);
		
		account.setCurrencyBalances(new HashMap<String, BigDecimal>());
		
		response = with().body(account)
							.contentType("application/json")
							.when()
							.request("POST", "/account")
							.then()
							.statusCode(400);
	}
}
