package com.snowwtarie.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Patient")
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_patient")
    @Getter @Setter private Long id;

    @Column(name = "nom", nullable = false)
    @Getter @Setter private String nom;

    @Column(name = "prenom", nullable = false)
    @Getter @Setter private String prenom;

    @Column(name = "dateOfBirth", nullable = false)
    @Temporal(TemporalType.DATE)
    @Getter @Setter private Date dateOfBirth;

    @Column(name = "adresse")
    @Getter @Setter private String adresse;

    @Column(name = "email")
    @Getter @Setter private String email;

    @Column(name = "telephone")
    @Getter @Setter private String telephone;
    
    @Column(name = "note")
    @Getter @Setter private String note;
    
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "id")
    @Getter @Setter private Praticien praticien;
    
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter @Setter List<RendezVous> rendezVous;
    
    public Patient() {
    	/*
    	 * Constructeur vide au cas ou
    	 */
    }
    
	public Patient(String pNom, String pPrenom, Date pDateOfBirth, String pAdresse, String pEmail, String pTelephone,
			String pNote, Praticien pPraticien) {
		super();
		this.nom = pNom;
		this.prenom = pPrenom;
		this.dateOfBirth = pDateOfBirth;
		this.adresse = pAdresse;
		this.email = pEmail;
		this.telephone = pTelephone;
		this.note = pNote;
		this.praticien = pPraticien;
	}
    
    
}
