package com.coding.task.messages.loading;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

//this class is meant to be used at application launch, throws exception to stop application startup
public class ClasspathResourceJsonMessageLoader {
	
	private static Logger log = Logger.getLogger(ClasspathResourceJsonMessageLoader.class);

	public static String loadMessageJsonStringFromClasspathResourceLocation(String classpathResourceLocation) throws IOException {
		log.info("ClasspathResourceJsonMessageLoader.loadMessageJsonStringFromClasspathResourceLocation() - classpathResourceLocation: " + classpathResourceLocation);
		
		//File resourceFile = ResourceUtils.getFile(classpathResourceLocation);
		InputStream resourceInputStream = null;
		String result = null;
		
		try {
			resourceInputStream = new DefaultResourceLoader().getResource(classpathResourceLocation).getInputStream();
		
			byte[] fileContent = FileCopyUtils.copyToByteArray(resourceInputStream);
		
			result = new String(fileContent);
		} finally {
			if(resourceInputStream != null) {
				try {
					resourceInputStream.close();
				} catch (IOException ioe) {
					//fail silently, its OK to fail closing a classpath resource that is meant to be read once in the application lifecycle
					log.error(ioe.getMessage(), ioe);
				}
			}
		}
		
		log.info("ClasspathResourceJsonMessageLoader.loadMessageJsonStringFromClasspathResourceLocation() - result: " + result);
		
		return result;
	}
	
	public static Map<String, String> loadMessageFromClasspathResourceLocation(String classpathResourceLocation) throws IOException {
		Map<String, String> result = null;
		
		String fileContent = ClasspathResourceJsonMessageLoader.loadMessageJsonStringFromClasspathResourceLocation(classpathResourceLocation);
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(fileContent, new TypeReference<Map<String, String>>(){});	
		} catch (Exception e) { //don't care exception type here as this is application start, log the error and stop the application startup
			log.error(e.getMessage(), e);
			
			throw e;
		}
		log.info("ClasspathResourceJsonMessageLoader.loadMessageFromClasspathResourceLocation() - result: " + result);
		
		return result;
	}
	
}
