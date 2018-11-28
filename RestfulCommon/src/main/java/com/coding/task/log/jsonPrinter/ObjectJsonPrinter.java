package com.coding.task.log.jsonPrinter;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectJsonPrinter {
	
	private static Logger log = Logger.getLogger(ObjectJsonPrinter.class);
	
	public static String printObjectAsJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String result = null;
		
		try {
			result = mapper.writeValueAsString(object);
		} catch (Exception e) {
			//fail silently
			log.error(e.getMessage(), e);
		}
		
		return result;
	}

}
