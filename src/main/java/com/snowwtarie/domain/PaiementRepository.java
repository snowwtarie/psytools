package com.snowwtarie.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;


@Transactional
public interface PaiementRepository extends CrudRepository<Paiement, Long> {
	public List<Praticien> findAllByPraticien(Praticien praticien);
}
