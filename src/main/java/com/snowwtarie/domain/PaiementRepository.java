package com.snowwtarie.domain;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


@Transactional
public interface PaiementRepository extends CrudRepository<Paiement, Long> {
	public List<Paiement> findAllByPraticien(Praticien praticien);
	public List<Paiement> findByDatePaiement(Date date);	
	@Query(value = "SELECT * FROM paiement WHERE EXTRACT(WEEK FROM date_paiement) = EXTRACT(WEEK FROM NOW())", nativeQuery = true)
	public List<Paiement> findByWeek();
	@Query(value = "SELECT * FROM paiement WHERE EXTRACT(MONTH FROM date_paiement) d = EXTRACT(MONTH FROM NOW()) ORDER BY d", nativeQuery = true)
	public List<Paiement> findByMonth();	
	@Query(value = "SELECT * FROM paiement WHERE EXTRACT(YEAR FROM date_paiement) = EXTRACT(YEAR FROM NOW())", nativeQuery = true)
	public List<Paiement> findByAnnee();
	public List<Paiement> findAllByDatePaiementBetween(Date dateUn, Date deux);
}
