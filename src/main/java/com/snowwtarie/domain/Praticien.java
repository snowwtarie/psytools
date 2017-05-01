package com.snowwtarie.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.Hibernate;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Praticien implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private Long id;

    @Column(name = "password", nullable = false)
    @Getter @Setter private String password;

    @Column(name = "nom", nullable = false)
    @Getter @Setter private String nom;

    @Column(name = "prenom", nullable = false)
    @Getter @Setter private String prenom;

    @Getter @Setter  private String adresse;
    
    @Column(name = "telephone", length = 20)
    @Getter @Setter private String telephone;

    @Column(name = "email", nullable = false, unique = true)
    @Getter @Setter private String email;
    
    @Column(name = "siret")
    @Getter @Setter private String siret;
    
    @OneToMany(mappedBy = "praticien", fetch= FetchType.LAZY, cascade = CascadeType.DETACH)
    @Getter @Setter private Set<Patient> patients;
    
    @OneToMany(mappedBy = "praticien", fetch= FetchType.LAZY, cascade = CascadeType.DETACH)
    @Getter @Setter private List<Paiement> listePaiements;
    
    public Praticien() {
        /**
         * Constructeur vide
         */
    }

    public Praticien(String pNom, String pPrenom) {
        this.nom = pNom;
        this.prenom = pPrenom;
    }

    public Praticien(String pNom, String pPrenom, String pEmail, String pPassword) {
        this.nom = pNom;
        this.prenom = pPrenom;
        this.email = pEmail;
        this.password = pPassword;
    }

    @Override
    public String toString() {
        return this.prenom + "::" + this.nom;
    }
    
    public void addPatient(Patient patient) {
    	this.getPatients().add(patient);
    }
}
