package com.bakirwebservice.securityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SecurityserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityserviceApplication.class, args);
	}

}
