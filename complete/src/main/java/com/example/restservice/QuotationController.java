package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class QuotationController {
	@Autowired
    private QuotationService quotationService;

	@GetMapping("/quotation")
	public String getQuotation() {
		ResponseEntity<String> response = quotationService.getQuotationJSON();
	    if(response.getStatusCode() == HttpStatus.OK) {
	        return response.getBody();
	    } else {
	        return null;
	    }
	}

}
