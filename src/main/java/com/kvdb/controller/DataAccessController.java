package com.kvdb.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kvdb.config.SysConfig;
import com.kvdb.model.KVStorage;
import com.kvdb.service.RestClientService;
import com.kvdb.utils.HashUtils;
import com.kvdb.utils.RestUtils;

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
	
	private Logger log = LoggerFactory.getLogger(DataAccessController.class.getName());

	@RequestMapping(value = RestUtils.SET+"/{key}", method = RequestMethod.POST)
	@ResponseBody
	public String set(@PathVariable("key") String key, @RequestBody String value,
			HttpServletResponse response) {
		log.debug("SET KEY " +key);
		String result = "SUCCESS";
		try {
			int nodeIndex = hashUtils.getNodeIndex(key);
			log.debug("SETTING KEY ON NODE " +nodeIndex);
			if(sysConfig.getNodeIdx() == nodeIndex){
				kvStorage.put(key, value);
			}
			else {
				int port = sysConfig.getNodePortMap().get(nodeIndex);
				result = restClientService.postValue(sysConfig.getHostName(), port, key, value);
			}
			postToReplica(nodeIndex, key, value);
		}
		catch(Exception e){
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return "FAILURE";
		}
		
		return result;

	}

	@RequestMapping(value = RestUtils.GET+"/{key}", method = RequestMethod.GET)
	@ResponseBody
	public String get(@PathVariable("key") String key, HttpServletResponse response) {
		log.debug("GET KEY " +key);
		String result = null;
		try{
			int nodeIndex = hashUtils.getNodeIndex(key);
			log.debug("GETTING KEY ON NODE " +nodeIndex);
			if(sysConfig.getNodeIdx() == nodeIndex){
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
	
	@RequestMapping(value = RestUtils.SET_REPLICA+"/{key}", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('SYSTEM_USER')")
	public String setReplica(@PathVariable("key") String key, @RequestBody String value,
			HttpServletResponse response) {
		log.debug("SET REPLICA KEY " +key);
		try{
			kvStorage.put(key, value);
		}
		catch(Exception e){
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return "FAILURE";
		}
		return "SUCCESS";
	}
	
	private void postToReplica(int primaryNodeIndex, String key, String value){
		
		if(!(sysConfig.getNumOfNodes() > 1)){
			return;
		}
		// post to replica
		int replicaPort = sysConfig.getNodePortMap().get(RestUtils.getReplicaNodeIdx(primaryNodeIndex, sysConfig.getNumOfNodes()));
		restClientService.postValueToReplica(sysConfig.getHostName(), replicaPort, key, value);
	}
}
