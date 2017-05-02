package com.snowwtarie.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snowwtarie.domain.Patient;
import com.snowwtarie.domain.RendezVous;
import com.snowwtarie.domain.RendezVousRepository;
import com.snowwtarie.service.ConstantesUtil.Creneau;

import lombok.Getter;

@Component("rendezVousService")
public class RendezVousService {
	
	@Autowired
	@Getter private RendezVousRepository repo;
	
	public List<RendezVous> getAllRendezVous(Patient patient) {
		List<RendezVous> liste = new ArrayList<>();
		
		liste = repo.findAllByPatient(patient);
		
		return liste;
	}
	
	public List<RendezVous> getAllRendezVousFutur(Patient patient) {
		List<RendezVous> liste = new ArrayList<>();
		
		liste = repo.findAllByPatientAndDateRendezVousGreaterThanOrDateRendezVousEquals(patient, Date.from(Instant.now()), Date.from(Instant.now()));
		
		return liste;
	}
	
	public List<RendezVous> getAllRendezVousPasse(Patient patient) {
		List<RendezVous> liste = new ArrayList<>();
		
		liste = repo.findAllByPatientAndDateRendezVousLessThan(patient, Date.from(Instant.now()));
		
		return liste;
	}
	
	public Map<Creneau, Boolean> getCreneaux() {
		Map<Creneau, Boolean> creneaux = new HashMap<>();
		
		for (Creneau c : Creneau.values()) {
			creneaux.put(c, true);
		}
		
		return creneaux;
	}
	
	public Map<Creneau, Boolean> getCreneauxDispos(Date dateRendezVous) {
		Map<Creneau, Boolean> creneaux;
		List<RendezVous> listeRdv = new ArrayList<>();
		
		listeRdv = repo.findAllByDateRendezVous(dateRendezVous);
		creneaux = getCreneaux();
		
		for (RendezVous rdv : listeRdv) {
			creneaux.put(rdv.getCreneau(), false);
		}
		
		return creneaux;
	}
}
