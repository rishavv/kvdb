package com.kvdb.listener;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.kvdb.config.SysConfig;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	SysConfig sysconfig;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {

		// generate node port map
		// <nodeid, node port>
		Integer numOfNodes = sysconfig.getNumOfNodes();
		sysconfig.setNodePortMap(new HashMap<Integer,Integer>());
		sysconfig.getNodePortMap().put(0, sysconfig.getMasterNodePort());
		for(int i=1; i<numOfNodes; i++){
			sysconfig.getNodePortMap().put(i, sysconfig.getMasterNodePort()+i);
		}
	}

}
