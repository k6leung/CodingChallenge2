package com.coding.task.RestTestCases.transactionDebitCreditRecordService;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import com.coding.task.records.model.CreditRecord;
import com.coding.task.records.model.DebitRecord;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestCreateDebitRecord {
	
	@Before
	public void setup() {
	    RestAssured.baseURI = "http://transaction-records.local.pcfdev.io/api";
	    RestAssured.port = 80;
	}

	@Test
	public void testCreateDebitRecord() {
		DebitRecord request = new DebitRecord(null,
												"00700",
												"HKD",
												new BigDecimal(10),
												LocalDateTime.now());
		
		Response response = with().body(request)
									.contentType("application/json")
									.when()
									.request("POST", "/debitRecord");

		response.then().statusCode(201);
		
		JsonPath jsonPath = new JsonPath(response.getBody().asInputStream());
		String id = jsonPath.getString("id");
		
		ValidatableResponse getResponse = when().get("/debitRecord/{id}", id)
													.then()
													.statusCode(200);
		
		getResponse.body("id", equalTo(id));
		getResponse.body("accountNumber", equalTo("00700"));
		getResponse.body("currency", equalTo("HKD"));
		getResponse.body("value", equalTo(10));
	}
	
	@Test
	public void testCreateDebitRecordNullOrEmptyAccountNumber() {
		DebitRecord request = new DebitRecord(null,
												null,
												"HKD",
												new BigDecimal(10),
												LocalDateTime.now());
		
		with().body(request)
				.contentType("application/json")
				.when()
				.request("POST", "/debitRecord")
				.then()
				.statusCode(400);
		
		request = new DebitRecord(null,
									"",
									"HKD",
									new BigDecimal(10),
									LocalDateTime.now());

		with().body(request)
				.contentType("application/json")
				.when()
				.request("POST", "/debitRecord")
				.then()
				.statusCode(400);
	}
	
	@Test
	public void testCreateDebitRecordUnsupportedCurrency() {
		DebitRecord request = new DebitRecord(null,
												"00701",
												"PHP",
												new BigDecimal(10),
												LocalDateTime.now());
		
		with().body(request)
				.contentType("application/json")
				.when()
				.request("POST", "/debitRecord")
				.then()
				.statusCode(400);
	}
	
	@Test
	public void testCreateDebitRecordNegativeValue() {
		DebitRecord request = new DebitRecord(null,
												"00702",
												"HKD",
												new BigDecimal(-10),
												LocalDateTime.now());

		with().body(request)
				.contentType("application/json")
				.when()
				.request("POST", "/debitRecord")
				.then()
				.statusCode(400);
	}
}
