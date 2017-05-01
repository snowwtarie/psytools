package com.snowwtarie.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.snowwtarie.domain.PaiementService.TypeReglement;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "paiement")
public class Paiement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "paiement_id")
	@Getter @Setter private Long id;
	
	@Column(name = "date_paiement")
	@Getter @Setter private Date datePaiement;
	
	@Column(name = "montant")
	@Getter @Setter private Float montant;

	@Column(name = "type_reglement")
	@Enumerated(EnumType.STRING)
	@Getter @Setter private TypeReglement typeReglement;
	
	@OneToOne(mappedBy = "paiement", cascade = CascadeType.DETACH)
	@Getter @Setter RendezVous rendezVous;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	@Getter @Setter Praticien praticien;
	
	public Paiement() {
		/**
		 * 
		 */
	}
	
	public Paiement(Date pDatePaiement, Float pMontant, TypeReglement pTypeReglement, RendezVous pRendezVous, Praticien pPraticien) {
		this.datePaiement = pDatePaiement;
		this.montant = pMontant;
		this.typeReglement = pTypeReglement;
		this.rendezVous = pRendezVous;
		this.praticien = pPraticien;
	}
}
