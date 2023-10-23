package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.restservice.services.PatientService;

@EnableJpaRepositories
@SpringBootApplication
public class RestServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
		PatientService.getPatientsFromJSON();
	}

}
