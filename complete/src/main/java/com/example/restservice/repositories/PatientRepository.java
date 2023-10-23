package com.example.restservice.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.restservice.models.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
	Patient findByName(String name);
	List<Patient> findAll();
}