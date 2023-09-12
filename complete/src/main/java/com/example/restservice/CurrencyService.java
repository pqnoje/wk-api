package com.example.restservice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {
	
	private final String CURRENCY_URL = "https://sdshealthcheck.cellologistics.com.br/sds-devs-evaluation/evaluation/currency"; 

    public ResponseEntity<String> getCurrenciesJSON() {
    	RestTemplate restTemplate = new RestTemplate();
    	return restTemplate.getForEntity(CURRENCY_URL, String.class);
    }
}