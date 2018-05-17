package com.kvdb.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.kvdb.model.KVStorage;
import com.kvdb.utils.HashUtils;

@Configuration
public class AppConfig {

	@Bean
	public MessageDigest messageDigest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("MD5");
	}
	
	@Bean
	public HashUtils hashUtils() {
		return new HashUtils();
	}
	
	@Bean
	public KVStorage<String,String> kvStorage(){
		return new KVStorage<String,String>(10000);
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate(); 
	}	
}
