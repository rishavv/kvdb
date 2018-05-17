package com.kvdb.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class KVStorage<K,V> {

	private Map<K,V> storageMap;
	
	public KVStorage(){
		this.storageMap = new HashMap<K,V>();
	}
	
	public KVStorage(int initCapacity){
		this.storageMap = new HashMap<K,V>(initCapacity);
	}
	
	public V get(K s){
		return storageMap.get(s);
	}
	
	public void put(K k, V v){
		this.storageMap.put(k, v);
	}
}
