package com.kvdb;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kvdb.utils.HashUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
public class HashUtilsTest {

	@Autowired
	HashUtils hashUtils;

	@Test
	public void validate_MD5() {
		byte[] hashed = hashUtils.getMd5Hash("key");
		String hexString = DatatypeConverter.printHexBinary(hashed);
		assertEquals("3C6E0B8A9C15224A8228B9A98CA1531D", hexString);

	}

	@Test
	public void validate_node_index_calc() {
		int nodeIndex = hashUtils.getNodeIndex("key");
		assertEquals(0, nodeIndex);
	}
	
}
