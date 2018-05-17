package com.kvdb.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;

import com.kvdb.config.SysConfig;

public class HashUtils {

	@Autowired
	MessageDigest messageDigest;
	
	@Autowired
	SysConfig sysConfig;
	
	public byte[] getMd5Hash(String s) throws UnsupportedEncodingException{
		byte[] input = s.getBytes("UTF-8");
		return messageDigest.digest(input);
	}
	
	public int getNodeIndex(String s) throws UnsupportedEncodingException {
		String hex = DatatypeConverter.printHexBinary(getMd5Hash(s));
		String decimal = new BigInteger(hex.substring(0,2), 16).toString(10);
		return Integer.parseInt(decimal)%sysConfig.getNumOfNodes();
	}
	
}
