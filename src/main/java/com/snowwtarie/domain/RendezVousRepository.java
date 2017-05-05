package com.snowwtarie.domain;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface RendezVousRepository extends CrudRepository<RendezVous, Long> {
	public List<RendezVous> findAllByDateRendezVous(Date dateRendezVous);
	public List<RendezVous> findAllByDateRendezVousAndPatient(Date dateRendezVous, Patient patient);
	public List<RendezVous> findAllByPatient(Patient patient);
	public List<RendezVous> findAllByPatientAndDateRendezVousLessThan(Patient patient, Date date);
	public List<RendezVous> findAllByPatientAndDateRendezVousGreaterThanOrDateRendezVousEquals(Patient patient, Date dateUn, Date deux);
	@Query(value = "SELECT * FROM rendez_vous WHERE EXTRACT(WEEK FROM date_rdv) = EXTRACT(WEEK FROM NOW()) AND patient_id = ?1", nativeQuery = true)
	public List<RendezVous> findByWeekAndPatient(Long id);
	@Query(value = "SELECT * FROM rendez_vous WHERE EXTRACT(MONTH FROM date_rdv) = EXTRACT(MONTH FROM NOW()) AND patient_id = ?1", nativeQuery = true)
	public List<RendezVous> findByMonthAndPatient(Long id);	
	@Query(value = "SELECT * FROM rendez_vous WHERE EXTRACT(YEAR FROM date_rdv) = EXTRACT(YEAR FROM NOW()) AND patient_id = ?1", nativeQuery = true)
	public List<RendezVous> findByAnnee();
	public List<RendezVous> findAllByDateRendezVousBetween(Date dateUn, Date deux);
	
}
