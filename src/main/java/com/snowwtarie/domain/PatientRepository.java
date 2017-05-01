package com.snowwtarie.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PatientRepository extends CrudRepository<Patient, Long> {
    Patient findByNom(String nom);
    Patient findByPrenom(String prenom);
    List<Patient> findByPraticien(Praticien praticien);
}
