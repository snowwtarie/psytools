package com.snowwtarie.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	
	public List<BeanData> trierRevenus(List<Paiement> listePaiement) {
		List<BeanData> data = new ArrayList<>();
		Iterator<Paiement> it = listePaiement.iterator();
		Float tmp = 0f;
		String dateTmp = "";
		
		while(it.hasNext()) {
			Paiement pmnt = it.next();
		}
		
		return data;
	}
	
}
