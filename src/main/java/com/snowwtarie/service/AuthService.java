package com.snowwtarie.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;

import com.snowwtarie.domain.Praticien;
import com.snowwtarie.domain.PraticienRepository;

@Component("authService")
public class AuthService {

    private final static ShaPasswordEncoder SHA = new ShaPasswordEncoder(512);

    @Resource
    private PraticienRepository repo;

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
}
