package com.kvdb.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class RestUtils {

	public static final String SET = "set";
	
	public static final String GET = "get";
	
	public static URI getCompleteURI(String host, int port, String path, String key) throws MalformedURLException, URISyntaxException{
		StringBuilder pathBuilder = new StringBuilder("/");
		pathBuilder.append(path);
		pathBuilder.append("/");
		pathBuilder.append(key);
		URI uri = new URI("http", null, host, port, pathBuilder.toString(), null, null);
		return uri;
	}
}
