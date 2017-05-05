package com.snowwtarie.domain;

import lombok.Getter;

public class BeanData {
	
	@Getter private String datePaiement;
	@Getter private Float montant;
	
	public BeanData(String pDatePaiement) {
		this.datePaiement = pDatePaiement;
		this.montant = 0f;
	}
	
	public void setMontant(Float pMontant) {
		if (pMontant != null) {
			this.montant += pMontant;
		}
	}
}
