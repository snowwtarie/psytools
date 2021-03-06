package com.snowwtarie.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConstantesUtil {

    public static final String ATTR_USER = "ATTR_USER";
    public static final String VIEW_TITLE = "title";
    public static final String URI = "uri";

    public ConstantesUtil() {
        /**
         * Classe utilitaire
         */
    }
    
    public static String formatPhone(String telephone) {
    	return telephone.replaceAll("[\\s\\._-]", "");
    }
    
    public static Date stringToDate(String dateString) {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
    	Date date = null;
    	
    	try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			System.err.println("Erreur lors du parsing de la date : " + e);
		}
    	
    	return date;
    }
    
    public static List<String> buildFilAriane(String referer) {
    	List<String> filAriane = new ArrayList<>();
    	String temp = referer;
    	
    	referer.replaceAll("http://localhost", "");
    	Collections.addAll(filAriane, referer.split("/"));
    	
    	return filAriane;
    }
    
    public enum Creneau {
    	UN(1),
    	DEUX(2),
    	TROIS(3),
    	QUATRE(4),
    	CINQ(5),
    	SIX(6),
    	SEPT(7);
    	
    	private int noCreneau = 0;
    	
    	Creneau(int pNoCreneau) {
    		this.noCreneau = pNoCreneau;
    	}
    }
    
    /**
     * Essayer de faire un fil d'ariane
     */
}


