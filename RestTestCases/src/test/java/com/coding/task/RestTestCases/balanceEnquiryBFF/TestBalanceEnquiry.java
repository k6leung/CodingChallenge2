package com.coding.task.RestTestCases.balanceEnquiryBFF;

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

public class TestBalanceEnquiry {
	
	private MultiCurrencyAccount account;
	
	@Before
	public void setup() {
		RestAssured.baseURI = "http://accounts.local.pcfdev.io/api";
	    RestAssured.port = 80;
	    
	  //create an account with 00500 account number, HKD 100
	    this.account = new MultiCurrencyAccount();
	    
	    account.setAccountNumber("00500");
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
		
		RestAssured.baseURI = "http://balance-enquiry.local.pcfdev.io/api";
	    
	}

	@Test
	public void testFindByAccountNumber() {
		ValidatableResponse response = when().get("/balance/{accountNumber}", "00500")
												.then()
												.statusCode(200);
		
		response.body("accountNumber", equalTo("00500"));
		response.body("currencyBalances.HKD", equalTo(100));
	}
	
	@Test
	public void testFindByAccountNumberFail() {
		ValidatableResponse response = when().get("/balance/{accountNumber}", "00501")
												.then()
												.statusCode(404);
	}

}
