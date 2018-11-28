package com.coding.task.rest.exception.controllerAdvice;

import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;

import com.coding.task.rest.errorCode.CommonStatusCode;
import com.coding.task.rest.exception.HttpStatusException;
import com.coding.task.rest.exception.message.GenericErrorMessage;
import com.coding.task.rest.exception.response.WebExceptionRestfulResponse;

@ControllerAdvice
public class CommonExceptionHandlerControllerAdvice {
	
	private static Logger log = Logger.getLogger(CommonExceptionHandlerControllerAdvice.class);
	
	@Autowired
	protected GenericErrorMessage genericErrorMessage;
	
	public CommonExceptionHandlerControllerAdvice() {
		super();
	}
	
	protected HttpHeaders generateJsonContentTypeHeader() {
		HttpHeaders result = new HttpHeaders();
		result.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		return result;
	}

	@ExceptionHandler(value= {HttpStatusException.class})
	public ResponseEntity<WebExceptionRestfulResponse> handleWebException(HttpStatusException hse) {
		log.error(hse.getMessage(), hse);
		
		WebExceptionRestfulResponse response = new WebExceptionRestfulResponse();
		
		HttpStatus httpStatus = hse.getHttpStatus();
		
		response.setErrorCode(hse.getErrorCode());
		response.setErrorMessages(hse.getErrorMessages());
		
		HttpHeaders headers = this.generateJsonContentTypeHeader();
		
		ResponseEntity<WebExceptionRestfulResponse> result = new ResponseEntity<WebExceptionRestfulResponse>(response,
																												headers,
																												httpStatus);
		
		return result;
	}
	
	@ExceptionHandler(value= {RestClientResponseException.class})
	public ResponseEntity<String> handleWebException(RestClientResponseException rcre) {
		//proxy the exception body and status code to response as this is most likely thrown by backend microservices, which supposedly always returns json on error.
		log.error(rcre.getMessage(), rcre);
		
		int statusCode = rcre.getRawStatusCode();
		HttpStatus resolvedStatus = HttpStatus.resolve(statusCode);
		
		String responseBody = rcre.getResponseBodyAsString();
		
		HttpHeaders headers = this.generateJsonContentTypeHeader();
		
		ResponseEntity<String> result = new ResponseEntity<String>(responseBody,
																	headers,
																	resolvedStatus);
		
		return result;
	}
	
	@ExceptionHandler(value= {RuntimeException.class, Exception.class})
	public ResponseEntity<WebExceptionRestfulResponse> handleWebException(Exception e) {
		log.error(e.getMessage(), e); //should also raise alert for support team to check
		
		WebExceptionRestfulResponse response = new WebExceptionRestfulResponse();
		
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		
		HttpHeaders headers = this.generateJsonContentTypeHeader();
		
		response.setErrorCode(CommonStatusCode.GENERIC_ERROR);
		
		//assume exception message is always English, use generic error message
		
		response.setErrorMessages(this.genericErrorMessage.getGenericErrorMessage());
		
		ResponseEntity<WebExceptionRestfulResponse> result = new ResponseEntity<WebExceptionRestfulResponse>(response,
																												headers,
																												httpStatus);
		
		return result;
	}
}
