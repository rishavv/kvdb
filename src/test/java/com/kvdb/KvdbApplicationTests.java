package com.kvdb;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kvdb.model.KVStorage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
public class KvdbApplicationTests {
	
	@MockBean
	private KVStorage<String,String> kvStorage;
	
    @Autowired
    private TestRestTemplate restTemplate;
	
	@Test
	public void doesGetWork() throws Exception {
		given(kvStorage.get("test1")).willReturn("test1value");
		
		ResponseEntity<String> response = restTemplate.getForEntity("/get/test1", String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(),"test1value");
	}
	
	@Test
	public void doesPostWork() throws Exception {
		
		ResponseEntity<String> response = restTemplate.postForEntity("/set/test1", "test1value", String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(),"SUCCESS");
	}
	
}
