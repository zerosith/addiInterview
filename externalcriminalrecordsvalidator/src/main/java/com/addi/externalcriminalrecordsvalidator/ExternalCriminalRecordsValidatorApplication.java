package com.addi.externalcriminalrecordsvalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableDiscoveryClient
public class ExternalCriminalRecordsValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExternalCriminalRecordsValidatorApplication.class, args);
	}

}