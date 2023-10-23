package com.example.restservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.models.ChartData;
import com.example.restservice.models.Patient;
import com.example.restservice.services.PatientService;

@CrossOrigin
@RestController
public class PatientController {
	@Autowired
    private PatientService patientService;

	@GetMapping("/patients")
	public ResponseEntity<List<Patient>> getPatients() {
		return new ResponseEntity<List<Patient>>(patientService.getAllPatients(), HttpStatus.OK); 
	}
	
	@GetMapping("/patients/by-state")
	public ResponseEntity<List<ChartData>> getPatientsByStateForChartData() {
		return new ResponseEntity<List<ChartData>>(patientService.getPatientsByStateForChartData(), HttpStatus.OK); 
	}
	
	@GetMapping("/patients/bmi-by-age-range")
	public ResponseEntity<List<ChartData>> getBMIByAgeRange() {
		return new ResponseEntity<List<ChartData>>(patientService.getBMIByAgeRange(), HttpStatus.OK); 
	}
	
	@GetMapping("/patients/fat-by-gender")
	public ResponseEntity<List<ChartData>> getFatByGender() {
		return new ResponseEntity<List<ChartData>>(patientService.getFatByGender(), HttpStatus.OK); 
	}
	
	@GetMapping("/patients/median-age-for-blood-type")
	public ResponseEntity<List<ChartData>> medianAgeForBloodType() {
		return new ResponseEntity<List<ChartData>>(patientService.medianAgeForBloodType(), HttpStatus.OK); 
	}
	
	@GetMapping("/patients/blood-donors")
	public ResponseEntity<List<ChartData>> getBloodDonors() {
		return new ResponseEntity<List<ChartData>>(patientService.getBloodDonors(), HttpStatus.OK); 
	}
}