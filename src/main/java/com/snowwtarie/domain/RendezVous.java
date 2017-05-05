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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
	
    @Column(name = "note_avant", columnDefinition = "TEXT")
	@Getter @Setter private String notesAvant;
	
    @Column(name = "note_apres", columnDefinition = "TEXT")
	@Getter @Setter private String notesApres;
	
    @Column(name = "paiement_status", columnDefinition = "boolean default false")
	@Getter @Setter private Boolean hasPaid;
    
    @Column(name = "status", columnDefinition = "boolean default false")
    @Getter @Setter private Boolean status;
	
	@Enumerated(EnumType.ORDINAL)
	@Getter @Setter private com.snowwtarie.service.ConstantesUtil.Creneau creneau;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	@Getter @Setter Paiement paiement;
	
	public RendezVous() {
		/**
		 * Au cas ou
		 */
	}
	
	public RendezVous(Patient pPatient, String pNomRendezVous, Date pDateRendezVous, String pNoteAvant) {
		new RendezVous(pPatient, pNomRendezVous, pDateRendezVous, pNoteAvant, "", false, false);
	}
	
	public RendezVous(Patient pPatient, String pNomRendezVous, Date pDateRendezVous, String pNoteAvant, String pNoteApres, Boolean pHasPaid, Boolean pStatus) {
		this.patient = pPatient;
		this.nomRendezVous = pNomRendezVous;
		this.dateRendezVous = pDateRendezVous;
		this.notesAvant = pNoteAvant;
		this.notesApres = pNoteApres;
		this.hasPaid = pHasPaid;
		this.status = pStatus;
	}
}
