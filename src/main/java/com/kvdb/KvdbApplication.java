package com.kvdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class KvdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(KvdbApplication.class, args);
	}
}
