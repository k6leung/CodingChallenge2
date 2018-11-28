package com.coding.task.RestTestCases.accountService;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.coding.task.account.fundTransfer.dto.AccountBalanceReceiveRequest;
import com.coding.task.account.model.MultiCurrencyAccount;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class TestCreateCredit {

	private MultiCurrencyAccount account;

	@Before
	public void setup() {
	    RestAssured.baseURI = "http://accounts.local.pcfdev.io/api";
	    RestAssured.port = 80;
	    
	    //create an account with 00400 account number, HKD 100
	    this.account = new MultiCurrencyAccount();
		
		account.setAccountNumber("00400");
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
	public void testCreateCredit() {
		AccountBalanceReceiveRequest request = new AccountBalanceReceiveRequest("00400",
																					"HKD",
																					new BigDecimal(10),
																					null,
																					LocalDateTime.now());
		
		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("PATCH", "/account/credit")
												.then()
												.statusCode(204);
		
		response = when().get("/account/accountNumber/{accountNumber}", "00400")
							.then();

		response.body("currencyBalances.HKD", equalTo(110));
	}
	
	@Test
	public void testCreateCreditNullOrEmptyAccountNumber() {
		AccountBalanceReceiveRequest request = new AccountBalanceReceiveRequest(null,
																					"HKD",
																					new BigDecimal(10),
																					null,
																					LocalDateTime.now());

		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("PATCH", "/account/credit")
												.then()
												.statusCode(400);
		
		response = when().get("/account/accountNumber/{accountNumber}", "00400")
							.then();

		response.body("currencyBalances.HKD", equalTo(100));
		
		request = new AccountBalanceReceiveRequest("",
													"HKD",
													new BigDecimal(10),
													null,
													LocalDateTime.now());

		response = with().body(request)
							.contentType("application/json")
							.when()
							.request("PATCH", "/account/credit")
							.then()
							.statusCode(400);
		
		response = when().get("/account/accountNumber/{accountNumber}", "00400")
							.then();

		response.body("currencyBalances.HKD", equalTo(100));
	}
	
	@Test
	public void testCreateCreditNonExistAccount() {
		AccountBalanceReceiveRequest request = new AccountBalanceReceiveRequest("00401",
																					"HKD",
																					new BigDecimal(10),
																					null,
																					LocalDateTime.now());

		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("PATCH", "/account/credit")
												.then()
												.statusCode(404);
	}
	
	@Test
	public void testCreateCreditUnsupportedCurrency() {
		AccountBalanceReceiveRequest request = new AccountBalanceReceiveRequest("00400",
																					"PHP",
																					new BigDecimal(10),
																					null,
																					LocalDateTime.now());
		
		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("PATCH", "/account/credit")
												.then()
												.statusCode(400);
		
		response = when().get("/account/accountNumber/{accountNumber}", "00400")
					.then();

		response.body("currencyBalances.HKD", equalTo(100));
		response.body("$", not(hasKey("currencyBalances.PHP")));
	}
	
	@Test
	public void testCreateCreditWithUsdForHkdAccount() {
		AccountBalanceReceiveRequest request = new AccountBalanceReceiveRequest("00400",
																					"USD",
																					new BigDecimal(10),
																					null,
																					LocalDateTime.now());

		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("PATCH", "/account/credit")
												.then()
												.statusCode(400);
		
		response = when().get("/account/accountNumber/{accountNumber}", "00400")
					.then();

		response.body("currencyBalances.HKD", equalTo(100));
		response.body("$", not(hasKey("currencyBalances.USD")));
	}
	
	@Test
	public void testCreateCreditWithNullOrNegativeValue() {
		AccountBalanceReceiveRequest request = new AccountBalanceReceiveRequest("00400",
																					"HKD",
																					null,
																					null,
																					LocalDateTime.now());
		
		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("PATCH", "/account/credit")
												.then()
												.statusCode(400);
		
		response = when().get("/account/accountNumber/{accountNumber}", "00400")
							.then();

		response.body("currencyBalances.HKD", equalTo(100));
		
		request = new AccountBalanceReceiveRequest("00400",
													"HKD",
													new BigDecimal(-1),
													null,
													LocalDateTime.now());
		
		response = with().body(request)
							.contentType("application/json")
							.when()
							.request("PATCH", "/account/credit")
							.then()
							.statusCode(400);
		
		response = when().get("/account/accountNumber/{accountNumber}", "00400")
							.then();

		response.body("currencyBalances.HKD", equalTo(100));

	}

}
