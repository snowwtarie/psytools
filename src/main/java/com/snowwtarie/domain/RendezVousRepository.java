package com.snowwtarie.domain;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface RendezVousRepository extends CrudRepository<RendezVous, Long> {
	public List<RendezVous> findAllByDateRendezVous(Date dateRendezVous);
	public List<RendezVous> findAllByPatient(Patient patient);
}
