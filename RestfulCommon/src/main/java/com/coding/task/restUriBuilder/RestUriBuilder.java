package com.coding.task.restUriBuilder;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.web.client.RestClientException;

public class RestUriBuilder {

	public static URI formUri(String host, String path) {
		URI result = null;
		try {
			result = new URI(host + path);
		} catch (URISyntaxException use) {
			throw new RestClientException(use.getMessage(), use);
		}
		
		return result;
	}
}
