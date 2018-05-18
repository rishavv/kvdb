package com.kvdb.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.kvdb.model.KVStorage;
import com.kvdb.utils.HashUtils;

@Configuration
public class AppConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public SysConfig sysConfig() {
		Integer numOfNodes = Integer.parseInt(env.getProperty("kvdb.num-of-nodes"));
		Integer nodeIdx = Integer.parseInt(env.getProperty("kvdb.node-idx"));
		Integer masterNodePort = Integer.parseInt(env.getProperty("kvdb.master-node-port"));
		String hostName = env.getProperty("kvdb.hostname");
		return new SysConfig(numOfNodes,nodeIdx,masterNodePort, hostName);
	}
	
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
