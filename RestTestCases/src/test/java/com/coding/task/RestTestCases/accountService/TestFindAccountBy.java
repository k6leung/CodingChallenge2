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
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class TestFindAccountBy {
	
	private MultiCurrencyAccount account;

	@Before
	public void setup() {
	    RestAssured.baseURI = "http://accounts.local.pcfdev.io/api";
	    RestAssured.port = 80;
	    
	    //create an account with 00200 account number, HKD 100
	    this.account = new MultiCurrencyAccount();
		
		account.setAccountNumber("00200");
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
	public void testFindById() {
		ValidatableResponse response = when().get("/account/{id}", this.account.getId())
												.then()
												.statusCode(200);
		
		response.body("id", equalTo(this.account.getId()));
		response.body("accountNumber", equalTo("00200"));
		response.body("currencyBalances.HKD", equalTo(100));
	}
	
	@Test
	public void testFindByIdFail() {
		ValidatableResponse response = when().get("/account/{id}", "blahblahblah")
										.then()
										.statusCode(404);
	}
	
	@Test
	public void testFindByAccountNumber() {
		ValidatableResponse response = when().get("/account/accountNumber/{accountNumber}", "00200")
												.then()
												.statusCode(200);

		response.body("id", equalTo(this.account.getId()));
		response.body("accountNumber", equalTo("00200"));
		response.body("currencyBalances.HKD", equalTo(100));
	}
	
	@Test
	public void testFindByAccountNumberFail() {
		ValidatableResponse response = when().get("/account/accountNumber/{accountNumber}", "00201")
												.then()
												.statusCode(404);
	}

}
