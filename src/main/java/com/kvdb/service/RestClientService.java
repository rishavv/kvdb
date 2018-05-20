package com.kvdb.service;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.kvdb.utils.RestUtils;

@Component
public class RestClientService {

	@Autowired
	RestTemplate restTemplate;
	
	private Logger log = LoggerFactory.getLogger(RestClientService.class.getName());
	
	public String getResponse(String host, int port, String key) throws RestClientException, URISyntaxException, MalformedURLException, ResourceAccessException{
		URI completeURI = RestUtils.getCompleteURI(host, port, RestUtils.GET, key);
        
		log.debug("URL "+completeURI.toString());
		HttpEntity<String> request = new HttpEntity<String>(new String());
		
		ResponseEntity<String> result = restTemplate.exchange(completeURI, HttpMethod.GET, request, String.class);

		return result.getBody();
	}
	
	public String postValue(String host, int port, String key, String value) throws MalformedURLException, RestClientException, URISyntaxException, ResourceAccessException{
		URI completeURI = RestUtils.getCompleteURI(host, port, RestUtils.SET, key);
		log.debug("URL "+completeURI.toString());
        
        HttpEntity<String> request = new HttpEntity<String>(new String(value));
        ResponseEntity<String> result = restTemplate.exchange(completeURI, HttpMethod.POST, request, String.class);
        
        return result.getBody();
	}
	

	@Async
	public void postValueToReplica(String host, int port, String key, String value){
		URI completeURI = null;
		try {
			completeURI = RestUtils.getCompleteURI(host, port, RestUtils.SET_REPLICA, key);
			log.debug("URL "+completeURI.toString());
			
			HttpEntity<String> request = new HttpEntity<String>(new String(value), createBasicAuthHeaders());
	        ResponseEntity<String> result = restTemplate.exchange(completeURI, HttpMethod.POST, request, String.class);
	        
	        if(!"SUCCESS".equalsIgnoreCase(result.getBody())){
	        	log.error("Replication FAILED - URI " +completeURI.toString());
	        }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ResourceAccessException e){
			e.printStackTrace();
			log.error("Replication FAILED - URI " +completeURI.toString());
		}
	}
	
	private HttpHeaders createBasicAuthHeaders() {
		return new HttpHeaders() {
			/**
			* 
			*/
			private static final long serialVersionUID = -4975297670294631609L;

			{
				String auth = RestUtils.SYSTEM_USER_NAME + ":" + RestUtils.SYSTEM_USER_PASSWORD;
				byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}
}
