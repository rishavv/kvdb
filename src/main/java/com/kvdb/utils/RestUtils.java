package com.kvdb.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class RestUtils {

	public static final String SET = "set";
	
	public static final String GET = "get";
	
	public static final String SET_REPLICA = "replica/set";
	
	public static final String SYSTEM_USER_NAME = "nstephenson";
	
	public static final String SYSTEM_USER_PASSWORD = "cryptonomicon";
	
	public static URI getCompleteURI(String host, int port, String path, String key) throws MalformedURLException, URISyntaxException{
		StringBuilder pathBuilder = new StringBuilder("/");
		pathBuilder.append(path);
		pathBuilder.append("/");
		pathBuilder.append(key);
		URI uri = new URI("http", null, host, port, pathBuilder.toString(), null, null);
		return uri;
	}
	
	public static int getReplicaNodeIdx(int primaryNodeIdx, int totalNodes){
		return (primaryNodeIdx + 1 == totalNodes) ? 0 : primaryNodeIdx + 1;
	}
}
