package com.coding.task.messages.format;

import java.io.IOException;

import com.coding.task.messages.format.exception.JsonConversionException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectJsonConverter {

	public static String convertObjectToJson(Object input) {
		String result = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			result = mapper.writeValueAsString(input);
		} catch (IOException ioe) { //thrown by jackson
			JsonConversionException translatedException = new JsonConversionException(ioe.getMessage(), ioe);
			
			throw translatedException;
		}
		
		return result;
	}
}
