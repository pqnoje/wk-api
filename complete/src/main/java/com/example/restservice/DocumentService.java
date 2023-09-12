package com.example.restservice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DocumentService {
	
	private final String DOCUMENT_URL = "https://sdshealthcheck.cellologistics.com.br/sds-devs-evaluation/evaluation/docs";

    public ResponseEntity<String> getDocumentJSON() {
    	RestTemplate restTemplate = new RestTemplate();
    	return restTemplate.getForEntity(DOCUMENT_URL, String.class);
    }
}