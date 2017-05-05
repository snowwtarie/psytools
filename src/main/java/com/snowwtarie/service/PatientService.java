package com.snowwtarie.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.snowwtarie.domain.Patient;
import com.snowwtarie.domain.PatientRepository;
import com.snowwtarie.domain.Praticien;

import lombok.Getter;

@Component("patientService")
public class PatientService {
	
	@Resource
	@Getter PatientRepository patientRepository;
	
	public Patient addPatient(
			String nom,
			String prenom,
			String dateOfBirth,
			String email,
			String telephone,
			String adresse,
			String ville,
			String codePostal,
			String notes,
			Praticien praticien) {
    	Patient patient = null;
    	Date date = ConstantesUtil.stringToDate(dateOfBirth);    	
    	
    	if(date != null) {
    		patient = new Patient(nom, prenom, date, adresse, ville, codePostal, email, ConstantesUtil.formatPhone(telephone), notes, praticien);
        	
        	try {
        		patient = patientRepository.save(patient);
        	} catch (Exception e) {
        		System.err.println("Erreur lors de la récupération du patient : " + e);
        	}
    	}
    	
    	return patient;
	}
	
	public List<Patient> listePatients(Praticien praticien) {
		List<Patient> list = null;
		
		list = patientRepository.findByPraticien(praticien);
		
		return list;
	}
	
	public Patient findById(String id) {
		return patientRepository.findOne(Long.parseLong(id));
	}
}
