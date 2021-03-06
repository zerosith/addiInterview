package com.external.nationalregistryvalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableDiscoveryClient
public class NationalregistryvalidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NationalregistryvalidatorApplication.class, args);
	}

}
