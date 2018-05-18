package com.kvdb.config;

import java.util.Map;

public class SysConfig {

	private Integer numOfNodes;

	private Integer nodeIdx;

	private Integer masterNodePort;
	
	private String hostName;
	
	public SysConfig(Integer numOfNodes, Integer nodeIdx, Integer masterNodePort, String hostName){
		this.numOfNodes = numOfNodes;
		this.nodeIdx = nodeIdx;
		this.masterNodePort = masterNodePort;
		this.hostName= hostName;
	}
	
	private Map<Integer,Integer> nodePortMap;
	
	public Integer getNumOfNodes() {
		return numOfNodes;
	}

	public void setNumOfNodes(Integer numOfNodes) {
		this.numOfNodes = numOfNodes;
	}

	public Integer getNodeIdx() {
		return nodeIdx;
	}

	public void setNodeIdx(Integer nodeIdx) {
		this.nodeIdx = nodeIdx;
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
