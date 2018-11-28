package com.coding.task.messages.format;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class ErrorMessageFormatter {

	public static Map<String, String> formatErrorMessageMap(Map<String, String> errorMessageMap, Object... contents) {
		Map<String, String> result = new HashMap<String, String>();
		
		for(String key : errorMessageMap.keySet()) {
			String errorMessageToBeFormatted = errorMessageMap.get(key);
			
			if(errorMessageToBeFormatted != null) {
				MessageFormat formatter = new MessageFormat(errorMessageToBeFormatted);
				
				result.put(key, formatter.format(contents));
			}
		}
		
		return result;
	}
	
}
