package com.phorest.salon.clientservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main Spring Boot Application
 *
 */
@SpringBootApplication
@EnableJpaAuditing
public class ClientServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientServicesApplication.class, args);
	}

}
