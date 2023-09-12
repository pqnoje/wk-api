package com.example.restservice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuotationService {
	
	private final String QUOTATION_URL = "https://sdshealthcheck.cellologistics.com.br/sds-devs-evaluation/evaluation/quotation";

    public ResponseEntity<String> getQuotationJSON() {
    	RestTemplate restTemplate = new RestTemplate();
    	return restTemplate.getForEntity(QUOTATION_URL, String.class);
    }
}