package com.coding.task.RestTestCases.transactionDebitCreditRecordService;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import com.coding.task.records.model.CreditRecord;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class TestCreateCreditRecord {

	@Before
	public void setup() {
	    RestAssured.baseURI = "http://transaction-records.local.pcfdev.io/api";
	    RestAssured.port = 80;
	}
	
	@Test
	public void testCreateCreditRecord() {
		CreditRecord request = new CreditRecord(null,
													"00600",
													"HKD",
													new BigDecimal(10),
													LocalDateTime.now());
		
		Response response = with().body(request)
									.contentType("application/json")
									.when()
									.request("POST", "/creditRecord");
		
		response.then().statusCode(201);
												
				
		JsonPath jsonPath = new JsonPath(response.getBody().asInputStream());
		String id = jsonPath.getString("id");
		
		ValidatableResponse getResponse = when().get("/creditRecord/{id}", id)
													.then()
													.statusCode(200);
		
		getResponse.body("id", equalTo(id));
		getResponse.body("accountNumber", equalTo("00600"));
		getResponse.body("currency", equalTo("HKD"));
		getResponse.body("value", equalTo(10));
	}
	
	@Test 
	public void testCreateCreditRecordNullOrEmptyAccountNumber() {
		CreditRecord request = new CreditRecord(null,
													null,
													"HKD",
													new BigDecimal(10),
													LocalDateTime.now());

		with().body(request)
				.contentType("application/json")
				.when()
				.request("POST", "/creditRecord")
				.then()
				.statusCode(400);
		
		request = new CreditRecord(null,
									"",
									"HKD",
									new BigDecimal(10),
									LocalDateTime.now());
		
		with().body(request)
				.contentType("application/json")
				.when()
				.request("POST", "/creditRecord")
				.then()
				.statusCode(400);
	}
	
	@Test
	public void testCreateCreditRecordUnsupportedCurrency() {
		CreditRecord request = new CreditRecord(null,
													"00601",
													"PHP",
													new BigDecimal(10),
													LocalDateTime.now());
		
		with().body(request)
				.contentType("application/json")
				.when()
				.request("POST", "/creditRecord")
				.then()
				.statusCode(400);
	}
	
	@Test
	public void testCreateCreditRecordNegativeValue() {
		CreditRecord request = new CreditRecord(null,
													"00602",
													"HKD",
													new BigDecimal(-10),
													LocalDateTime.now());

		with().body(request)
				.contentType("application/json")
				.when()
				.request("POST", "/creditRecord")
				.then()
				.statusCode(400);
	}
}
