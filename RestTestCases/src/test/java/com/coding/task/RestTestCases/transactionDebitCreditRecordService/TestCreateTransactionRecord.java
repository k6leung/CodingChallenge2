package com.coding.task.RestTestCases.transactionDebitCreditRecordService;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import com.coding.task.records.model.CreditRecord;
import com.coding.task.records.model.DebitRecord;
import com.coding.task.records.model.TransactionRecord;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestCreateTransactionRecord {

	private DebitRecord debitRecord;
	
	private CreditRecord creditRecord;
	
	@Before
	public void setup() {
	    RestAssured.baseURI = "http://transaction-records.local.pcfdev.io/api";
	    RestAssured.port = 80;
	    
	    this.debitRecord = new DebitRecord(null,
											"00800",
											"HKD",
											new BigDecimal(10),
											LocalDateTime.now());
	    
	    Response response = with().body(this.debitRecord)
									.contentType("application/json")
									.when()
									.request("POST", "/debitRecord");
	    
	    JsonPath jsonPath = new JsonPath(response.getBody().asInputStream());
		String id = jsonPath.getString("id");
		this.debitRecord.setId(id);
		
		this.creditRecord = new CreditRecord(null,
												"00801",
												"HKD",
												new BigDecimal(10),
												LocalDateTime.now());
		
		response = with().body(this.creditRecord)
							.contentType("application/json")
							.when()
							.request("POST", "/creditRecord");
		
		jsonPath = new JsonPath(response.getBody().asInputStream());
		id = jsonPath.getString("id");
		this.creditRecord.setId(id);
	}
	
	@Test
	public void testCreateTransactionRecord() {
		TransactionRecord request = new TransactionRecord(null,
															this.debitRecord.getId(),
															this.creditRecord.getId(),
															LocalDateTime.now());
		
		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("POST", "/transactionRecord")
												.then()
												.statusCode(201);
	}
	
	@Test
	public void testCreateTransactionRecordDebitIdNullOrEmptyOrNonExist() {
		TransactionRecord request = new TransactionRecord(null,
															null,
															this.creditRecord.getId(),
															LocalDateTime.now());
		
		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("POST", "/transactionRecord")
												.then()
												.statusCode(500);
		
		request = new TransactionRecord(null,
											"",
											this.creditRecord.getId(),
											LocalDateTime.now());
		
		response = with().body(request)
							.contentType("application/json")
							.when()
							.request("POST", "/transactionRecord")
							.then()
							.statusCode(404);
		
		request = new TransactionRecord(null,
											"1234",
											this.creditRecord.getId(),
											LocalDateTime.now());

		response = with().body(request)
					.contentType("application/json")
					.when()
					.request("POST", "/transactionRecord")
					.then()
					.statusCode(404);
	}
	
	@Test
	public void testCreateTransactionRecordCreditIdNullOrEmptyOrNonExist() {
		TransactionRecord request = new TransactionRecord(null,
															this.debitRecord.getId(),
															null,
															LocalDateTime.now());

		ValidatableResponse response = with().body(request)
												.contentType("application/json")
												.when()
												.request("POST", "/transactionRecord")
												.then()
												.statusCode(500);

		request = new TransactionRecord(null,
											this.debitRecord.getId(),
											"",
											LocalDateTime.now());

		response = with().body(request)
							.contentType("application/json")
							.when()
							.request("POST", "/transactionRecord")
							.then()
							.statusCode(404);

		request = new TransactionRecord(null,
											this.debitRecord.getId(),
											"1234",
											LocalDateTime.now());

		response = with().body(request)
							.contentType("application/json")
							.when()
							.request("POST", "/transactionRecord")
							.then()
							.statusCode(404);
	}
}
