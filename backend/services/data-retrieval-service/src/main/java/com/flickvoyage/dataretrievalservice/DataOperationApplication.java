package com.flickvoyage.dataretrievalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DataOperationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataOperationApplication.class, args);
	}

}
