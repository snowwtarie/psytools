package com.snowwtarie.domain;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component("paiementService")
public class PaiementService {

	@Resource
	@Getter PaiementRepository repo;
	
	public enum TypeReglement {
		ESPECES("especes"),
		CHEQUE("cheque"),
		CB("carte_bancaire"),
		AUTRE("autre");
		
		private String typePaiement = "";
		
		TypeReglement(String pTypePaiement) {
			this.typePaiement = pTypePaiement;
		}
	}
	
}
