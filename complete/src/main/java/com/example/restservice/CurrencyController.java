package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class CurrencyController {
	@Autowired
    private CurrencyService currencyService;

	@GetMapping("/currency")
	public String getCurrency() {
		ResponseEntity<String> response = currencyService.getCurrenciesJSON();
	    if(response.getStatusCode() == HttpStatus.OK) {
	        return response.getBody();
	    } else {
	        return null;
	    }
	}

}
