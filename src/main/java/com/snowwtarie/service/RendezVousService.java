package com.snowwtarie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snowwtarie.domain.Patient;
import com.snowwtarie.domain.RendezVous;
import com.snowwtarie.domain.RendezVousRepository;

@Component("rendezVousService")
public class RendezVousService {
	
	@Autowired
	private RendezVousRepository repo;
	
	public List<RendezVous> getAllRendezVous(Patient patient) {
		List<RendezVous> liste = new ArrayList<>();
		
		liste = repo.findAllByPatient(patient);
		
		return liste;
	}
}
