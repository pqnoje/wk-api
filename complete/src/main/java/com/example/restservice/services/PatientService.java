package com.example.restservice.services;

import java.io.FileReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restservice.models.ChartData;
import com.example.restservice.models.Patient;
import com.example.restservice.repositories.PatientRepository;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

@Service
public class PatientService {
	
	static PatientRepository patientReporisory;
	
    @Autowired
    public PatientService(PatientRepository patientReporisory) {
        this.patientReporisory = patientReporisory;
    }
	
    public static void getPatientsFromJSON() {
    	JSONParser parser = new JSONParser();
    	 try {
             Object obj = parser.parse(new FileReader("./patients.json"));
             JSONArray jsonObject = (JSONArray) obj;
             Iterator<JSONObject> iterator = jsonObject.iterator();
             
             while (iterator.hasNext()) {
            	 Patient patient = new Patient();
            	 JSONObject patientJSONObject = iterator.next();
            	 patient.setName((String) patientJSONObject.get("nome"));
            	 patient.setCpf((String) patientJSONObject.get("cpf"));
            	 patient.setRg((String) patientJSONObject.get("rg"));
            	 patient.setBirthdate(Date.valueOf(LocalDate.parse( 
         			 	(String) patientJSONObject.get("data_nasc"), 
        			    DateTimeFormatter.ofPattern( "dd/MM/yyyy"))));
            	 patient.setGender((String) patientJSONObject.get("sexo"));
            	 patient.setMotherName((String) patientJSONObject.get("mae"));
            	 patient.setFatherName((String) patientJSONObject.get("pai"));
            	 patient.setEmail((String) patientJSONObject.get("email"));
            	 patient.setCep((String) patientJSONObject.get("cep"));
            	 patient.setAddress((String) patientJSONObject.get("endereco"));
            	 patient.setAddressNumber((long) patientJSONObject.get("numero"));
            	 patient.setNeighborhood((String) patientJSONObject.get("bairro"));
            	 patient.setCity((String) patientJSONObject.get("cidade"));
            	 patient.setState((String) patientJSONObject.get("estado"));
            	 patient.setPhone((String) patientJSONObject.get("telefone_fixo"));
            	 patient.setMobile((String) patientJSONObject.get("celular"));
            	 if(patientJSONObject.get("altura").getClass() == Long.class) {
            		 double weight = ((Long) patientJSONObject.get("altura")).doubleValue(); 
            		 patient.setStature(weight);
            	 } else {
            		 patient.setStature((double) patientJSONObject.get("altura"));	 
            	 }
            	 patient.setWeight((long) patientJSONObject.get("peso"));
            	 patient.setBloodType((String) patientJSONObject.get("tipo_sanguineo"));
            	 patientReporisory.save(patient);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
    
    public List<Patient> getAllPatients() {
    	return patientReporisory.findAll();
    }
    
    public List<ChartData> getBloodDonors() {
    	List<Patient> patients = patientReporisory.findAll();
    	List<ChartData> chartData = new ArrayList<ChartData>();
    	
    	LocalDate today = new java.util.Date().toInstant()
    			.atZone(ZoneId.systemDefault()).toLocalDate();
    	patients.forEach(patient -> patient.setAge(Period.between(
    			patient.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
    			today).getYears()));
    	
    	List<Patient> aPositive = patients.stream()
				.filter(patient -> patient.getBloodType().equals("A+") &&
						patient.getAge() >= 16 &&
						patient.getAge() <= 69 &&
						patient.getWeight() >= 50)
				.collect(Collectors.toList());
    	
    	List<Patient> aNegative = patients.stream()
				.filter(patient -> patient.getBloodType().equals("A-") &&
						patient.getAge() >= 16 &&
						patient.getAge() <= 69 &&
						patient.getWeight() >= 50)
				.collect(Collectors.toList());
    	
    	List<Patient> bPositive = patients.stream()
				.filter(patient -> patient.getBloodType().equals("B+") &&
						patient.getAge() >= 16 &&
						patient.getAge() <= 69 &&
						patient.getWeight() >= 50)
				.collect(Collectors.toList());
    	
    	List<Patient> bNegative = patients.stream()
				.filter(patient -> patient.getBloodType().equals("B-") &&
						patient.getAge() >= 16 &&
						patient.getAge() <= 69 &&
						patient.getWeight() >= 50)
				.collect(Collectors.toList());
    	
    	List<Patient> abPositive = patients.stream()
				.filter(patient -> patient.getBloodType().equals("AB+") &&
						patient.getAge() >= 16 &&
						patient.getAge() <= 69 &&
						patient.getWeight() >= 50)
				.collect(Collectors.toList());
    	
    	List<Patient> abNegative = patients.stream()
				.filter(patient -> patient.getBloodType().equals("AB-") &&
						patient.getAge() >= 16 &&
						patient.getAge() <= 69 &&
						patient.getWeight() >= 50)
				.collect(Collectors.toList());
    	
    	List<Patient> oPositive = patients.stream()
				.filter(patient -> patient.getBloodType().equals("O+") &&
						patient.getAge() >= 16 &&
						patient.getAge() <= 69 &&
						patient.getWeight() >= 50)
				.collect(Collectors.toList());
    	
    	List<Patient> oNegative = patients.stream()
				.filter(patient -> patient.getBloodType().equals("O-") &&
						patient.getAge() >= 16 &&
						patient.getAge() <= 69 &&
						patient.getWeight() >= 50)
				.collect(Collectors.toList());
    	
    	ChartData aPositiveDonor = new ChartData();
    	aPositiveDonor.setName("A+");
    	aPositiveDonor.setValue(aPositive.size() + 
    		      aNegative.size() +
    		      oPositive.size() +
    		      oNegative.size());
    	chartData.add(aPositiveDonor);
    	
    	ChartData aNegativeDonor = new ChartData();
    	aNegativeDonor.setName("A-");
    	aNegativeDonor.setValue(aNegative.size() +
    		      oNegative.size());
    	chartData.add(aNegativeDonor);
    	
    	ChartData bPositiveDonor = new ChartData();
    	bPositiveDonor.setName("B+");
    	bPositiveDonor.setValue(bPositive.size() +
    		      bNegative.size() +
    		      oPositive.size() +
    		      oNegative.size());
    	chartData.add(bPositiveDonor);
    	
    	ChartData bNegativeDonor = new ChartData();
    	bNegativeDonor.setName("B-");
    	bNegativeDonor.setValue(bNegative.size() +
    		      oNegative.size());
    	chartData.add(bNegativeDonor);
    	
    	ChartData abPositiveDonor = new ChartData();
    	abPositiveDonor.setName("AB+");
    	abPositiveDonor.setValue(abNegative.size() +
    		      aPositive.size() +
    		      bPositive.size() +
    		      oPositive.size() +
    		      abPositive.size() +
    		      aNegative.size() +
    		      bNegative.size() +
    		      oNegative.size());
    	chartData.add(abPositiveDonor);
    	
    	ChartData abNegativeDonor = new ChartData();
    	abNegativeDonor.setName("AB-");
    	abNegativeDonor.setValue(abPositive.size() +
    		      aNegative.size() +
    		      bNegative.size() +
    		      oNegative.size());
    	chartData.add(abNegativeDonor);
    	
    	ChartData oPositiveDonor = new ChartData();
    	oPositiveDonor.setName("O+");
    	oPositiveDonor.setValue(oNegative.size() +
    		      oPositive.size());
    	chartData.add(oPositiveDonor);
    	
    	ChartData oNegativeDonor = new ChartData();
    	oNegativeDonor.setName("O-");
    	oNegativeDonor.setValue( oNegative.size());
    	chartData.add(oNegativeDonor);

    	return chartData;
    }
    
    public List<ChartData> medianAgeForBloodType() {
    	List<Patient> patients = patientReporisory.findAll();
    	List<ChartData> chartData = new ArrayList<ChartData>();
    	
    	LocalDate today = new java.util.Date().toInstant()
    			.atZone(ZoneId.systemDefault()).toLocalDate();
    	patients.forEach(patient -> patient.setAge(Period.between(
    			patient.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
    			today).getYears()));
    	
    	List<Patient> aPositive = patients.stream()
				.filter(patient -> patient.getBloodType().equals("A+"))
					.collect(Collectors.toList());
    	List<Patient> aNegative = patients.stream()
				.filter(patient -> patient.getBloodType().equals("A-"))
				.collect(Collectors.toList());
		List<Patient> bPositive = patients.stream()
				.filter(patient -> patient.getBloodType().equals("B+"))
				.collect(Collectors.toList());
		List<Patient> bNegative = patients.stream()
				.filter(patient -> patient.getBloodType().equals("B-"))
				.collect(Collectors.toList());
		List<Patient> abPositive = patients.stream()
				.filter(patient -> patient.getBloodType().equals("AB+"))
				.collect(Collectors.toList());
		List<Patient> abNegative = patients.stream()
				.filter(patient -> patient.getBloodType().equals("AB-"))
				.collect(Collectors.toList());
		List<Patient> oPositive = patients.stream()
				.filter(patient -> patient.getBloodType().equals("O+"))
				.collect(Collectors.toList());
		List<Patient> oNegative = patients.stream()
				.filter(patient -> patient.getBloodType().equals("O-"))
				.collect(Collectors.toList());
		
		double aPositiveMedian = aPositive.stream()
		  .map(patient -> patient.getAge())
		  .reduce(0, Integer::sum);
		double aNegativeMedian = aNegative.stream()
				  .map(patient -> patient.getAge())
				  .reduce(0, Integer::sum);
		double bPositiveMedian = bPositive.stream()
				  .map(patient -> patient.getAge())
				  .reduce(0, Integer::sum);
		double bNegativeMedian = bNegative.stream()
				  .map(patient -> patient.getAge())
				  .reduce(0, Integer::sum);
		double abPositiveMedian = abPositive.stream()
				  .map(patient -> patient.getAge())
				  .reduce(0, Integer::sum);
		double abNegativeMedian = abNegative.stream()
				  .map(patient -> patient.getAge())
				  .reduce(0, Integer::sum);
		double oPositiveMedian = oPositive.stream()
				  .map(patient -> patient.getAge())
				  .reduce(0, Integer::sum);
		double oNegativeMedian = oNegative.stream()
				  .map(patient -> patient.getAge())
				  .reduce(0, Integer::sum);
		
		ChartData aPositiveChartData = new ChartData();
		aPositiveChartData.setName("A+");
		aPositiveChartData.setValue(aPositiveMedian / aPositive.size());
    	chartData.add(aPositiveChartData);
    	
    	ChartData aNegativeChartData = new ChartData();
    	aNegativeChartData.setName("A-");
    	aNegativeChartData.setValue(aNegativeMedian / aNegative.size());
    	chartData.add(aNegativeChartData);
    	
    	ChartData bPositiveChartData = new ChartData();
    	bPositiveChartData.setName("B+");
    	bPositiveChartData.setValue(bPositiveMedian / bPositive.size());
    	chartData.add(bPositiveChartData);
    	
    	ChartData bNegativeChartData = new ChartData();
    	bNegativeChartData.setName("B-");
    	bNegativeChartData.setValue(bNegativeMedian / bNegative.size());
    	chartData.add(bNegativeChartData);
    	
    	ChartData abPositiveChartData = new ChartData();
    	abPositiveChartData.setName("AB+");
    	abPositiveChartData.setValue(abPositiveMedian / abPositive.size());
    	chartData.add(abPositiveChartData);
    	
    	ChartData abNegativeChartData = new ChartData();
    	abNegativeChartData.setName("AB-");
    	abNegativeChartData.setValue(abNegativeMedian / abNegative.size());
    	chartData.add(abNegativeChartData);
    	
    	ChartData oPositiveChartData = new ChartData();
		oPositiveChartData.setName("O+");
		oPositiveChartData.setValue(oPositiveMedian / oPositive.size());
    	chartData.add(oPositiveChartData);
    	
    	ChartData oNegativeChartData = new ChartData();
    	oNegativeChartData.setName("O-");
    	oNegativeChartData.setValue(oNegativeMedian / oNegative.size());
    	chartData.add(oNegativeChartData);
    	
		return chartData;
    }

    public List<ChartData> getFatByGender() {
    	List<Patient> patients = patientReporisory.findAll();
    	List<ChartData> chartData = new ArrayList<ChartData>();
    	
    	patients.forEach(patient -> patient.setFatCondition(
    			patient.getWeight() / (patient.getStature() * patient.getStature()) > 30));
    	
    	List<Patient> fatMales = patients.stream()
				.filter(patient -> patient.isFatCondition() && 
        			patient.getGender().equals("Masculino"))
				.collect(Collectors.toList());
    	
    	List<Patient> fatFemales = patients.stream()
				.filter(patient -> patient.isFatCondition() && 
        			patient.getGender().equals("Feminino"))
				.collect(Collectors.toList());
    	
    	ChartData chartDataMale = new ChartData();
    	chartDataMale.setName("Masculino");
    	chartDataMale.setValue(fatMales.size());
    	chartData.add(chartDataMale);
    	ChartData chartDataFemale = new ChartData();
    	chartDataFemale.setName("Feminino");
    	chartDataFemale.setValue(fatFemales.size());
    	chartData.add(chartDataFemale);
    	
    	return chartData;
    }
    
    
    public List<ChartData> getPatientsByStateForChartData() {
    	List<Patient> patients = patientReporisory.findAll();
    	List<ChartData> filteredPatientsByState = new ArrayList<ChartData>();
    	List<ChartData> sortedPatientsByState = null;
    	
    	for (int i = 0; i < patients.size(); i++) {
    		final int iCopy = i;
    		ChartData chartDataExist = filteredPatientsByState
    				.stream().filter(
    				chartData -> chartData.getName().equals(patients.get(iCopy).getState()))
    				.findFirst()
    				.orElse(null);
    		if(chartDataExist != null) {
    			chartDataExist.setValue(chartDataExist.getValue() + 1);
    		} else {
    			ChartData chartData = new ChartData();
    			chartData.setName(patients.get(i).getState());
    			chartData.setValue(1);
    			filteredPatientsByState.add(chartData);
    		}
    	}
    	
    	sortedPatientsByState = filteredPatientsByState
				.stream().sorted(Comparator.comparing(ChartData::getName))
                .collect(Collectors.toList());
    	return sortedPatientsByState;
    }
    
    public List<ChartData> getBMIByAgeRange() {
    	List<Patient> patients = patientReporisory.findAll();
    	List<ChartData> filteredPatients = new ArrayList<ChartData>();
    	LocalDate today = new java.util.Date().toInstant()
    			.atZone(ZoneId.systemDefault()).toLocalDate();
    	patients.forEach(patient -> patient.setAge(Period.between(
    			patient.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
    			today).getYears()));
    	patients.forEach(patient -> patient.setBmi(
    			patient.getWeight() / (patient.getStature() * patient.getStature())));
    	Patient patientMaxAge = patients
    		      .stream()
    		      .max(Comparator.comparing(Patient::getAge))
    		      .orElseThrow(NoSuchElementException::new);
    	
    	for (int i = 0; i < patientMaxAge.getAge(); i = i + 10) {
    		final int iFinal = i;
    		List<Patient> filteredPatientsByAgeRage = patients.stream()
    				.filter(patient -> patient.getAge() >= iFinal + 1 && 
            			patient.getAge() <= iFinal + 10)
    				.collect(Collectors.toList());
    		double medianBMI = 0;
    		for (int j = 0; j < filteredPatientsByAgeRage.size(); j++) {
    			medianBMI += filteredPatientsByAgeRage.get(j).getBmi();
    		}
    		if(filteredPatientsByAgeRage.size() == 0) {
    			medianBMI = 0;
    		} else {
    			medianBMI = medianBMI / filteredPatientsByAgeRage.size();
    		}
    		ChartData chartData = new ChartData();
    		int initialRange = i == 0 ? 0 : i + 1;
    		int finalRange = i + 10;
    		chartData.setName(initialRange + " - " + finalRange);
    		chartData.setValue(medianBMI);
    		filteredPatients.add(chartData);
    	}
    	
    	return filteredPatients;
    }
}