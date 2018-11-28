package com.coding.task.RestTestCases.accountService;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.coding.task.account.fundTransfer.dto.AccountBalanceWithdrawRequest;
import com.coding.task.account.model.MultiCurrencyAccount;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class TestCreateDebit {
	
	private MultiCurrencyAccount account;

	@Before
	public void setup() {
	    RestAssured.baseURI = "http://accounts.local.pcfdev.io/api";
	    RestAssured.port = 80;
	    
	    //create an account with 00300 account number, HKD 100
	    this.account = new MultiCurrencyAccount();
		
		account.setAccountNumber("00300");
		Map<String, BigDecimal> currencyBalanceMap = new HashMap<String, BigDecimal>();
		currencyBalanceMap.put("HKD", new BigDecimal(100));
		account.setCurrencyBalances(currencyBalanceMap);
		
		Response response = with().body(account)
									.contentType("application/json")
									.when()
									.request("POST", "/account");
		
		JsonPath jsonPath = new JsonPath(response.getBody().asInputStream());
		
		String id = jsonPath.getString("id");
		this.account.setId(id);
		
	}
	
	@Test
	public void testCreateDebit() {
		AccountBalanceWithdrawRequest request = new AccountBalanceWithdrawRequest("00300",
																					"HKD",
																					new BigDecimal(10),
																					null,
																					LocalDateTime.now());
		
		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("PATCH", "/account/debit")
												.then()
												.statusCode(204);
		
		response = when().get("/account/accountNumber/{accountNumber}", "00300")
							.then();
		
		response.body("currencyBalances.HKD", equalTo(90));
		
	}
	
	@Test
	public void testCreateDebitEmptyOrNullAccountNumber() {
		AccountBalanceWithdrawRequest request = new AccountBalanceWithdrawRequest(null,
																					"HKD",
																					new BigDecimal(2000),
																					null,
																					LocalDateTime.now());

		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("PATCH", "/account/debit")
												.then()
												.statusCode(400);
		
		response = when().get("/account/accountNumber/{accountNumber}", "00300")
							.then();
		
		response.body("currencyBalances.HKD", equalTo(100));
		
		request = new AccountBalanceWithdrawRequest("",
														"HKD",
														new BigDecimal(2000),
														null,
														LocalDateTime.now());

		response = with().body(request)
							.contentType("application/json")
							.when()
							.request("PATCH", "/account/debit")
							.then()
							.statusCode(400);
		
		response = when().get("/account/accountNumber/{accountNumber}", "00300")
							.then();

		response.body("currencyBalances.HKD", equalTo(100));
	}
	
	@Test
	public void testCreateDebitNonExistAccount() {
		AccountBalanceWithdrawRequest request = new AccountBalanceWithdrawRequest("00301",
																					"HKD",
																					new BigDecimal(2000),
																					null,
																					LocalDateTime.now());

		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("PATCH", "/account/debit")
												.then()
												.statusCode(404);
	}
	
	@Test
	public void testCreateDebitInsufficientFund() {
		AccountBalanceWithdrawRequest request = new AccountBalanceWithdrawRequest("00300",
																					"HKD",
																					new BigDecimal(2000),
																					null,
																					LocalDateTime.now());
		
		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("PATCH", "/account/debit")
												.then()
												.statusCode(400);
		
		response = when().get("/account/accountNumber/{accountNumber}", "00300")
							.then();

		response.body("currencyBalances.HKD", equalTo(100));
	}
	
	@Test
	public void testCreateDebitUnsupportedCurrency() {
		AccountBalanceWithdrawRequest request = new AccountBalanceWithdrawRequest("00300",
																					"PHP",
																					new BigDecimal(20),
																					null,
																					LocalDateTime.now());

		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("PATCH", "/account/debit")
												.then()
												.statusCode(400);
		
		response = when().get("/account/accountNumber/{accountNumber}", "00300")
							.then();

		response.body("currencyBalances.HKD", equalTo(100));
		response.body("$", not(hasKey("currencyBalances.PHP")));
	}
	
	@Test
	public void testCreateDebitWithUsdForHkdAccount() {
		AccountBalanceWithdrawRequest request = new AccountBalanceWithdrawRequest("00300",
																					"USD",
																					new BigDecimal(20),
																					null,
																					LocalDateTime.now());

		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("PATCH", "/account/debit")
												.then()
												.statusCode(400);
		
		response = when().get("/account/accountNumber/{accountNumber}", "00300")
							.then();

		response.body("currencyBalances.HKD", equalTo(100));
		response.body("$", not(hasKey("currencyBalances.USD")));
	}
	
	@Test
	public void testCreateDebitWithNullOrNegativeValue() {
		AccountBalanceWithdrawRequest request = new AccountBalanceWithdrawRequest("00300",
																					"HKD",
																					null,
																					null,
																					LocalDateTime.now());
		
		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("PATCH", "/account/debit")
												.then()
												.statusCode(400);

		response = when().get("/account/accountNumber/{accountNumber}", "00300")
							.then();
		
		response.body("currencyBalances.HKD", equalTo(100));
		
		request = new AccountBalanceWithdrawRequest("00300",
														"HKD",
														new BigDecimal(-1),
														null,
														LocalDateTime.now());
		
		response = with().body(request)
							.contentType("application/json")
							.when()
							.request("PATCH", "/account/debit")
							.then()
							.statusCode(400);

		response = when().get("/account/accountNumber/{accountNumber}", "00300")
							.then();
		
		response.body("currencyBalances.HKD", equalTo(100));
	}

}
