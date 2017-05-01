package com.snowwtarie.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.snowwtarie.domain.Patient;
import com.snowwtarie.domain.Praticien;
import com.snowwtarie.service.AuthService;
import com.snowwtarie.service.ConstantesUtil;
import com.snowwtarie.service.PatientService;
import com.snowwtarie.service.RendezVousService;

@Controller
public class FrontController {
	
	@Resource
	private AuthService authService;
	
	@Resource
	private PatientService patientService;
	
	@Resource
	private RendezVousService rendezVousService;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request, ModelMap map) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "views/auth/login";
		} else {
			map.put(ConstantesUtil.VIEW_TITLE, "Home");
			return "views/home";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet(HttpServletRequest request, ModelMap map) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) != null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Home");
			return "views/home";
		} else {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "views/auth/login";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(@RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes attr, HttpServletRequest request, ModelMap map) {
		String view;
		Praticien praticien;

        praticien = authService.login(password, email);

        if (praticien == null) {
            map.put("login_fail", "Mauvaise combinaison email - mot de passe.");
            view = "views/auth/login";
        } else {
            attr.addFlashAttribute("loggedIn", true);
            request.getSession().setAttribute(ConstantesUtil.ATTR_USER, praticien);
            view = "redirect:login";
        }

        return view;
	}

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signinGet(HttpServletRequest request, ModelMap map) {
    	if(request.getSession().getAttribute(ConstantesUtil.ATTR_USER) != null) {
    		map.put(ConstantesUtil.VIEW_TITLE, "Home");
    		return "views/home";
    	} else {
    		map.put(ConstantesUtil.VIEW_TITLE, "Inscription");
    		return "views/auth/signin";
    	}
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String signinPost(
    		@RequestParam(value="inputNom") String inputNom,
    		@RequestParam(value="inputPrenom") String inputPrenom,
    		@RequestParam(value="inputEmail")  String inputEmail,
    		@RequestParam(value="inputPassword")  String inputPassword,
    		RedirectAttributes attr,
    		HttpServletRequest request,
    		ModelMap map) {
    	String view;
        Praticien praticien = null;

        praticien = authService.signin(inputPassword, inputNom, inputPrenom, inputEmail);

        if (praticien.getId() == null) {
            map.put("usr_exist", "L'adresse email " + inputEmail + " est déjà utilisée.");
    		map.put(ConstantesUtil.VIEW_TITLE, "Inscription");
            view = "views/auth/signin";
        } else {
            attr.addFlashAttribute("signedIn", true);
            request.getSession().setAttribute(ConstantesUtil.ATTR_USER, praticien);
            view = "redirect:home";        	
        }

        return view;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String login(HttpServletRequest request, ModelMap map, RedirectAttributes attr) {
    	request.getSession().invalidate();    	
    	
    	map.put(ConstantesUtil.VIEW_TITLE, "Login");
    	
    	return "redirect:login";
    }

    @RequestMapping(value = "/home/patient", method = RequestMethod.GET)
    public String indexPatient(HttpServletRequest request, ModelMap map) {
    	map.put(ConstantesUtil.VIEW_TITLE, "Patients");
    	map.put(
    			"patients",
    					patientService.listePatients((Praticien) request.getSession().getAttribute(ConstantesUtil.ATTR_USER))
					);
    	
    	return "views/patient/index";
    }

    @RequestMapping(value = "/home/patient/new", method = RequestMethod.GET)
    public String newPatient(HttpServletRequest request, ModelMap map) {
    	map.put(ConstantesUtil.VIEW_TITLE, "Nouveau patient");
    	
    	return "views/patient/new";
    }

    @RequestMapping(value = "/home/patient/new", method = RequestMethod.POST)
    public String newPatientPost(
    		@RequestParam(value="nom") String nom,
    		@RequestParam(value="prenom") String prenom,
    		@RequestParam(value="dob")  String dateOfBirth,
    		@RequestParam(value="email")  String email,
    		@RequestParam(value="phone")  String telephone,
    		@RequestParam(value="adresse", required=false)  String adresse,
    		@RequestParam(value="notes", required=false)  String notes,
    		HttpServletRequest request,
    		ModelMap map) {    	
    	Patient patient;
    	Praticien praticien;
    	
    	praticien = (Praticien) request.getSession().getAttribute(ConstantesUtil.ATTR_USER);
    	
    	patient = patientService.addPatient(nom, prenom, dateOfBirth, email, telephone, adresse, notes, praticien);
    	
    	if (patient == null) {
    		map.put("save_fail", "Enregistrement impossible");
    		
    		return "views/patient/index";
    	} else {
    		map.put(ConstantesUtil.VIEW_TITLE, "Patients");
    		
    		return "redirect:/home/patient";
    	}    	
    }
    
    @RequestMapping(value = "/home/patient/{id}", method = RequestMethod.GET)
    public String viewPatient(HttpServletRequest request, ModelMap map, @PathVariable String id) {
    	Patient patient = patientService.findById(id);
    	
    	map.put("patient", patient);
		map.put(ConstantesUtil.VIEW_TITLE, "Consultation profil");
    	map.put("rendezVous", rendezVousService.getAllRendezVous(patient));
    	
    	return "views/patient/view";
    }
}
