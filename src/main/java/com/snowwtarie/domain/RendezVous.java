package com.snowwtarie.domain;

import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "rendez_vous")
public class RendezVous {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_rdv")
	@Getter @Setter private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_patient")
	@Getter @Setter private Patient patient;
	
	@Column(name = "nom_rdv")
	@Getter @Setter private String nomRendezVous;

    @Column(name = "date_rdv", nullable = false)
    @Temporal(TemporalType.DATE)
    @Getter @Setter private Date dateRendezVous;
	
    @Column(name = "note_avant")
	@Getter @Setter private String noteAvant;
	
    @Column(name = "note_apres")
	@Getter @Setter private String noteApres;
	
    @Column(name = "paiement_status", columnDefinition = "boolean default false")
	@Getter @Setter private Boolean hasPaid;
	
	@Enumerated(EnumType.ORDINAL)
	@Getter @Setter private com.snowwtarie.service.ConstantesUtil.Creneau creneau;
	
	public RendezVous() {
		/**
		 * Au cas ou
		 */
	}
	
	public RendezVous(Patient pPatient, String pNomRendezVous, Date pDateRendezVous, String pNoteAvant) {
		this.patient = pPatient;
		this.nomRendezVous = pNomRendezVous;
		this.dateRendezVous = pDateRendezVous;
		this.noteAvant = pNoteAvant;
		this.noteApres = "";
		this.hasPaid = false;
	}
	
	public RendezVous(Patient pPatient, String pNomRendezVous, Date pDateRendezVous, String pNoteAvant, String pNoteApres, Boolean pHasPaid) {
		new RendezVous(pPatient, pNomRendezVous, pDateRendezVous, pNoteAvant);
		this.noteApres = pNoteApres;
		this.hasPaid = pHasPaid;
	}
}