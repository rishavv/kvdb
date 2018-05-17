package com.kvdb.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="kvdb")
@Component
public class SysConfig {

	private Integer numOfNodes;

	private String nodeType;
	
	private Integer nodeId;

	private Integer masterNodePort;
	
	private String hostName;
	
	private Map<Integer,Integer> nodePortMap;
	
	public Integer getNumOfNodes() {
		return numOfNodes;
	}

	public void setNumOfNodes(Integer numOfNodes) {
		this.numOfNodes = numOfNodes;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public Map<Integer, Integer> getNodePortMap() {
		return nodePortMap;
	}

	public void setNodePortMap(Map<Integer, Integer> nodePortMap) {
		this.nodePortMap = nodePortMap;
	}

	public Integer getMasterNodePort() {
		return masterNodePort;
	}

	public void setMasterNodePort(Integer masterNodePort) {
		this.masterNodePort = masterNodePort;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	
}
