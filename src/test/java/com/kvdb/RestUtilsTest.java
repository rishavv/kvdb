package com.kvdb;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kvdb.utils.RestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
public class RestUtilsTest {
	
	@Test
	public void validate_URL(){
		try {
			URI uri = RestUtils.getCompleteURI("127.0.0.1", 6666, RestUtils.GET, "key");
			System.out.println(uri.toString());
			assertEquals("http://127.0.0.1:6666/get/key",uri.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validate_replica_node_idx_calc1(){
		int replicaNodeIdx = RestUtils.getReplicaNodeIdx(0, 3);
		assertEquals(1,replicaNodeIdx);
	}
	
	@Test
	public void validate_replica_node_idx_calc2(){
		int replicaNodeIdx = RestUtils.getReplicaNodeIdx(2, 3);
		assertEquals(0,replicaNodeIdx);
	}
}
