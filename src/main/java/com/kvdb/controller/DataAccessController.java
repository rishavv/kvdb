package com.kvdb.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kvdb.RestClientService;
import com.kvdb.config.SysConfig;
import com.kvdb.model.KVStorage;
import com.kvdb.utils.HashUtils;

@RestController
public class DataAccessController {
	
	@Autowired
	KVStorage<String, String> kvStorage;
	
	@Autowired
	HashUtils hashUtils;
	
	@Autowired
	SysConfig sysConfig;
	
	@Autowired
	RestClientService restClientService;

	@RequestMapping(value = "{key}", method = RequestMethod.POST)
	@ResponseBody
	public String put(@PathVariable("key") String key, @RequestBody String value,
			HttpServletResponse response) {
		try {
			int nodeIndex = hashUtils.getNodeIndex(key);
			if(sysConfig.getNodeId() == nodeIndex){
				kvStorage.put(key, value);
			}
			else {
				int port = sysConfig.getNodePortMap().get(nodeIndex);
				String result = restClientService.postValue(sysConfig.getHostName(), port, key, value);
				return result;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return "FAILURE";
		}
		
		return "SUCCESS";

	}

	@RequestMapping(value = "{key}", method = RequestMethod.GET)
	@ResponseBody
	public String patientOptOut(@PathVariable("key") String key, HttpServletResponse response) {
		String result = null;
		try{
			int nodeIndex = hashUtils.getNodeIndex(key);
			if(sysConfig.getNodeId() == nodeIndex){
				result = kvStorage.get(key);
			}
			else {
				int port = sysConfig.getNodePortMap().get(nodeIndex);
				result = restClientService.getResponse(sysConfig.getHostName(), port, key);
			}
			if(null == result){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
		return result;

	}
	
}
