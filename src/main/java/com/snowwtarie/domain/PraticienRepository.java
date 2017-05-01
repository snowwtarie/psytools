package com.snowwtarie.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PraticienRepository extends CrudRepository<Praticien, Long> {

    Praticien findByNomAndPrenom(String nom, String prenom);
    Praticien findByEmail(String email);
    Praticien findByEmailAndPassword(String email, String password);
}
