package com.kvdb;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.kvdb.utils.RestUtils;

@Component
public class RestClientService {

	@Autowired
	RestTemplate restTemplate;
	
	public String getResponse(String host, int port, String key) throws RestClientException, URISyntaxException, MalformedURLException{
		URI completeURI = RestUtils.getCompleteURI(host, port, RestUtils.GET, key);
        
        System.out.println("URL "+completeURI.toString());
		HttpEntity<String> request = new HttpEntity<String>(new String());
		
		ResponseEntity<String> result = restTemplate.exchange(completeURI, HttpMethod.GET, request, String.class);

		return result.getBody();
	}
	
	public String postValue(String host, int port, String key, String value) throws MalformedURLException, RestClientException, URISyntaxException{
		URI completeURI = RestUtils.getCompleteURI(host, port, RestUtils.SET, key);
        System.out.println("URL "+completeURI.toString());
        
        HttpEntity<String> request = new HttpEntity<String>(new String(value));
        ResponseEntity<String> result = restTemplate.exchange(completeURI, HttpMethod.POST, request, String.class);
        
        return result.getBody();
	}
	
}
