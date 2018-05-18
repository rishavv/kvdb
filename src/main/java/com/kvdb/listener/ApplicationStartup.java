package com.kvdb.listener;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.kvdb.config.SysConfig;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private SysConfig sysconfig;
	
	@Autowired
	private ConfigurableApplicationContext ctx;
	
	private Logger log = LoggerFactory.getLogger(ApplicationStartup.class.getName());
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent arg0) {

		if(!validateConfig()){
			return;
		}
		
		Integer numOfNodes = sysconfig.getNumOfNodes();
		// generate node port map
		// <nodeid, node port>
		sysconfig.setNodePortMap(new HashMap<Integer,Integer>());
		sysconfig.getNodePortMap().put(0, sysconfig.getMasterNodePort());
		for(int i=1; i<numOfNodes; i++){
			sysconfig.getNodePortMap().put(i, sysconfig.getMasterNodePort()+i);
		}
		log.info("KVDB STARTED WITH FOLLOWING CONFIG");
		log.info("NUMBER OF NODES " +sysconfig.getNumOfNodes());
		log.info("CURRENT NODE INDEX " +sysconfig.getNodeIdx());
	}

	private boolean validateConfig(){
		if(sysconfig.getNumOfNodes() > 255 || sysconfig.getNumOfNodes() < 1){
			log.error("KVDB START UP FAILED - INVALID num-of-nodes" +sysconfig.getNumOfNodes());
			ctx.close();
			return false;
		}
		return true;
	}
}
