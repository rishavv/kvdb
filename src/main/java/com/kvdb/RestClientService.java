package com.kvdb;


import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClientService {

	@Autowired
	RestTemplate restTemplate;
	
	public String getResponse(String host, int port, String key) throws RestClientException, URISyntaxException, MalformedURLException{
		String protocol = "http";
        URL url = new URL (protocol, host, port, "/"+key);
        System.out.println("URL "+url.toString());
		HttpEntity<String> request = new HttpEntity<String>(new String());
		
		ResponseEntity<String> result = restTemplate.exchange(url.toURI(), HttpMethod.GET, request, String.class);

		return result.getBody();
	}
	
	public String postValue(String host, int port, String key, String value) throws MalformedURLException, RestClientException, URISyntaxException{
		String protocol = "http";
        URL url = new URL (protocol, host, port, "/"+key);
        System.out.println("URL "+url.toString());
        
        HttpEntity<String> request = new HttpEntity<String>(new String(value));
        ResponseEntity<String> result = restTemplate.exchange(url.toURI(), HttpMethod.POST, request, String.class);
        
        return result.getBody();
	}
}
