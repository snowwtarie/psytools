package com.snowwtarie.service;

import javax.annotation.Resource;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;

import com.snowwtarie.domain.Praticien;
import com.snowwtarie.domain.PraticienRepository;

import lombok.Getter;

@Component("authService")
public class AuthService {

    private final static ShaPasswordEncoder SHA = new ShaPasswordEncoder(512);

    @Resource
    @Getter private PraticienRepository repo;

    public Praticien login(String password, String email) {
        Praticien praticien = null;
        String hashedPassword;

        try {
            hashedPassword = SHA.encodePassword(password, "");
            praticien = repo.findByEmail(email);
            if (praticien != null) {
                praticien = repo.findByEmailAndPassword(email, hashedPassword);
            }
        } catch (Exception any) {
            System.err.println("Erreur lors de la récupération de l'utilisateur : " + any);
        }

        return praticien;
    }

    public Praticien signin(String inputPassword, String inputNom, String inputPrenom, String inputEmail) {
        Praticien praticien = null;
        String hashedPassword;

        try {
            hashedPassword = SHA.encodePassword(inputPassword, "");
            praticien = new Praticien(inputNom, inputPrenom, inputEmail, hashedPassword);

            if (repo.findByNomAndPrenom(inputNom, inputPrenom) != null) {
                praticien = new Praticien();
            } else {
                repo.save(praticien);
            }
        } catch (Exception any) {
            System.err.println("Erreur lors de la création de l'utilisateur: " + any.toString());
        }

        return praticien;
    }
    
    public String encodePassword(String password) {
    	return SHA.encodePassword(password, "");
    }
    
    public boolean checkPassword(String password, String passwordToCheck) {
    	boolean samePwd = true;
    	
    	String hashedPassword = SHA.encodePassword(passwordToCheck, "");
    	
    	if (!password.equals(hashedPassword)) {
    		samePwd = false;
    	}
    	
    	return samePwd;
    }
}
