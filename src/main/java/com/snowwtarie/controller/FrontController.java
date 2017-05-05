package com.snowwtarie.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.snowwtarie.domain.BeanData;
import com.snowwtarie.domain.Paiement;
import com.snowwtarie.domain.PaiementService;
import com.snowwtarie.domain.PaiementService.TypeReglement;
import com.snowwtarie.domain.Patient;
import com.snowwtarie.domain.Praticien;
import com.snowwtarie.domain.RendezVous;
import com.snowwtarie.service.AuthService;
import com.snowwtarie.service.ConstantesUtil;
import com.snowwtarie.service.ConstantesUtil.Creneau;
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

	@Resource
	private PaiementService paiementService;

	@ModelAttribute
	public void initMap(HttpServletRequest request, ModelMap map) {
		// map.put("ariane",
		// ConstantesUtil.buildFilAriane(request.getHeader("referer")));
		map.put(ConstantesUtil.URI, request.getRequestURI());
		// Si pas de header referer
		// Renvoyer vers home avec message d'avertissement
		// Sinon -> bouton retour
		if (request.getHeader("referer") != null) {
			map.put("retour", request.getHeader("referer"));
			map.put(ConstantesUtil.VIEW_TITLE, "test");
		} else {
			System.err.println("Par l'url !");
		}
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request, ModelMap map) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			map.put(ConstantesUtil.VIEW_TITLE, "Home");
			map.put("praticien", (Praticien) request.getSession().getAttribute(ConstantesUtil.ATTR_USER));
			return "views/home";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet(HttpServletRequest request, ModelMap map) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) != null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Home");
			return "redirect:/home";
		} else {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "views/auth/login";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(@RequestParam("email") String email, @RequestParam("password") String password,
			RedirectAttributes attr, HttpServletRequest request, ModelMap map) {
		String view;
		Praticien praticien;

		praticien = authService.login(password, email);

		if (praticien == null) {
			map.put("login_fail", "Mauvaise combinaison email - mot de passe.");
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			view = "views/auth/login";
		} else {
			attr.addFlashAttribute("loggedIn", true);
			map.put(ConstantesUtil.VIEW_TITLE, "Home");
			request.getSession().setAttribute(ConstantesUtil.ATTR_USER, praticien);
			view = "redirect:/home";
		}

		return view;
	}

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signinGet(HttpServletRequest request, ModelMap map) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) != null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Home");
			return "redirect:/login";
		} else {
			map.put(ConstantesUtil.VIEW_TITLE, "Inscription");
			return "views/auth/signin";
		}
	}

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String signinPost(@RequestParam(value = "inputNom") String inputNom,
			@RequestParam(value = "inputPrenom") String inputPrenom,
			@RequestParam(value = "inputEmail") String inputEmail,
			@RequestParam(value = "inputPassword") String inputPassword, RedirectAttributes attr,
			HttpServletRequest request, ModelMap map) {
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
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			map.put(ConstantesUtil.VIEW_TITLE, "Patients");
			map.put("patients", patientService
					.listePatients((Praticien) request.getSession().getAttribute(ConstantesUtil.ATTR_USER)));

			return "views/patient/index";
		}
	}

	@RequestMapping(value = "/home/patient/new", method = RequestMethod.GET)
	public String newPatient(HttpServletRequest request, ModelMap map) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			map.put(ConstantesUtil.VIEW_TITLE, "Nouveau patient");

			return "views/patient/new";
		}
	}

	@RequestMapping(value = "/home/patient/new", method = RequestMethod.POST)
	public String newPatientPost(@RequestParam(value = "nom") String nom, @RequestParam(value = "prenom") String prenom,
			@RequestParam(value = "dob") String dateOfBirth, @RequestParam(value = "email") String email,
			@RequestParam(value = "phone") String telephone,
			@RequestParam(value = "adresse", required = false) String adresse,
			@RequestParam(value = "ville", required = false) String ville,
			@RequestParam(value = "cp", required = false) String cp,
			@RequestParam(value = "notes", required = false) String notes, HttpServletRequest request, ModelMap map) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			Patient patient;
			Praticien praticien;

			praticien = (Praticien) request.getSession().getAttribute(ConstantesUtil.ATTR_USER);

			patient = patientService.addPatient(nom, prenom, dateOfBirth, email, telephone, adresse, ville, cp, notes,
					praticien);

			if (patient == null) {
				map.put("save_fail", "Enregistrement impossible");

				return "views/patient/index";
			} else {
				map.put(ConstantesUtil.VIEW_TITLE, "Patients");

				return "redirect:/home/patient";
			}
		}
	}

	@RequestMapping(value = "/home/patient-{id}", method = RequestMethod.GET)
	public String viewPatient(HttpServletRequest request, ModelMap map, @PathVariable String id) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			Patient patient = patientService.findById(id);

			map.put(ConstantesUtil.VIEW_TITLE, "Consultation profil");
			map.put(ConstantesUtil.URI, request.getRequestURI());
			map.put("patient", patient);
			map.put("rendezVousPasse", rendezVousService.getAllRendezVousPasse(patient));
			map.put("rendezVousFutur", rendezVousService.getAllRendezVousFutur(patient));

			return "views/patient/view";
		}
	}

	@RequestMapping(value = "/home/patient-{id}", method = RequestMethod.POST)
	public String updatePatient(HttpServletRequest request, ModelMap map, @PathVariable String id,
			@RequestParam(name = "nom", required = false) String nom,
			@RequestParam(name = "prenom", required = false) String prenom,
			@RequestParam(name = "dob", required = false) String dateOfBirth,
			@RequestParam(name = "adresse", required = false) String adresse,
			@RequestParam(name = "ville", required = false) String ville,
			@RequestParam(name = "cp", required = false) String cp,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "phone", required = false) String telephone,
			@RequestParam(name = "notes", required = false) String notes) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			Patient patient = patientService.findById(id);
			boolean saveOk = true;

			patient.setNom(nom);
			patient.setPrenom(prenom);
			patient.setDateOfBirth(ConstantesUtil.stringToDate(dateOfBirth));
			patient.setAdresse(adresse);
			patient.setVille(ville);
			patient.setCodePostal(cp);
			patient.setEmail(email);
			patient.setTelephone(telephone);
			patient.setNote(notes);

			try {
				patientService.getPatientRepository().save(patient);
			} catch (Exception e) {
				System.err.println("Erreur lors de la sauvegarde du profil.");
				saveOk = false;
			}

			map.put(ConstantesUtil.VIEW_TITLE, "Consultation profil");
			map.put(ConstantesUtil.URI, request.getRequestURI());
			map.put("patient", patient);
			map.put("rendezVousPasse", rendezVousService.getAllRendezVousPasse(patient));
			map.put("rendezVousFutur", rendezVousService.getAllRendezVousFutur(patient));
			map.put("patientOk", saveOk);

			return "views/patient/view";
		}
	}

	@RequestMapping(value = "/home/patient-{id}/rdv-{rdvId}", method = RequestMethod.GET)
	public String viewRendezVous(HttpServletRequest request, ModelMap map, @PathVariable String id,
			@PathVariable String rdvId) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			Patient patient = patientService.findById(id);
			RendezVous rdv = null;
			Map<Creneau, Boolean> creneaux;

			if ("new".equals(rdvId)) {
				map.put("newRdv", true);
				creneaux = rendezVousService.getCreneaux();
			} else {
				rdv = rendezVousService.getRepo().findOne(Long.parseLong(rdvId));
				creneaux = rendezVousService.getCreneauxDispos(rdv.getDateRendezVous());
				map.put("rendezVous", rdv);
			}
			map.put(ConstantesUtil.VIEW_TITLE, "Rendez-vous - Nouveau");
			map.put(ConstantesUtil.URI, request.getRequestURI());
			map.put("patient", patient);
			map.put("creneaux", creneaux.entrySet());

			return "views/patient/rdv";
		}
	}

	@RequestMapping(value = "/home/patient-{id}/rdv-{rdvId}/cancel", method = RequestMethod.GET)
	public String cancelRendezVous(HttpServletRequest request, ModelMap map, @PathVariable String id,
			@PathVariable String rdvId) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			Patient patient = patientService.findById(id);
			RendezVous rdv = rendezVousService.getRepo().findOne(Long.parseLong(rdvId));

			map.put(ConstantesUtil.VIEW_TITLE, "Consultation profil");
			map.put(ConstantesUtil.URI, request.getRequestURI());
			map.put("patient", patient);

			rendezVousService.getRepo().delete(rdv);

			return "redirect:/home/patient-" + id;
		}
	}

	@RequestMapping(value = "/home/patient-{id}/rdv-{rdvId}/close", method = RequestMethod.GET)
	public String closeRendezVous(HttpServletRequest request, ModelMap map, @PathVariable String id,
			@PathVariable String rdvId) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			Patient patient = patientService.findById(id);
			RendezVous rdv = rendezVousService.getRepo().findOne(Long.parseLong(rdvId));
			
			rdv.setStatus(true);
			rendezVousService.getRepo().save(rdv);

			map.put(ConstantesUtil.VIEW_TITLE, "Consultation profil");
			map.put(ConstantesUtil.URI, request.getRequestURI());
			map.put("patient", patient);

			return "redirect:/home/patient-" + id;
		}
	}

	@RequestMapping(value = "/home/patient-{id}/rdv-{rdvId}", method = RequestMethod.POST)
	public String postRendezVous(HttpServletRequest request, ModelMap map, @PathVariable String id,
			@PathVariable String rdvId, @RequestParam(value = "dateRdv", required = false) String dateRdv,
			@RequestParam(value = "nomRdv", required = false) String nomRdv,
			@RequestParam(value = "notesAvant", required = false) String notesAvant,
			@RequestParam(value = "notesApres", required = false) String notesApres,
			@RequestParam(value = "creneau", required = false) String creneau) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			Patient patient = patientService.findById(id);
			RendezVous rdv = null;
			boolean notesOk = false;
			String idRdv = rdvId;

			if ("new".equals(rdvId)) {
				rdv = new RendezVous(patient, nomRdv, ConstantesUtil.stringToDate(dateRdv), notesAvant, notesApres,
						false, false);
				try {
					rendezVousService.getRepo().save(rdv);
					map.put("rendezVous", rdv);
					map.put("ajoutOk", false);
					rdvId = String.valueOf(rdv.getId());
				} catch (Exception e) {
					System.err.println("Erreur lors de l'ajout du rendez-vous");
				}
			} else {
				rdv = rendezVousService.getRepo().findOne(Long.parseLong(rdvId));
				if (notesAvant != null) {
					rdv.setNotesAvant(notesAvant);
					notesOk = true;
				}
				if (notesApres != null) {
					rdv.setNotesApres(notesApres);
					notesOk = true;
				}
				if (creneau != null) {
					rdv.setCreneau(Creneau.valueOf(creneau));
				}
				map.put("notesOk", notesOk);
				map.put("rendezVous", rdv);
			}

			rendezVousService.getRepo().save(rdv);

			map.put(ConstantesUtil.VIEW_TITLE, "Rendez-vous");
			map.put(ConstantesUtil.URI, request.getRequestURI());
			map.put("patient", patient);
			map.put("creneaux", rendezVousService.getCreneauxDispos(rdv.getDateRendezVous()).entrySet());

			return "redirect:/home/patient-"+id+"/rdv-"+rdvId;
		}
	}

	@RequestMapping(value = "/home/patient-{id}/rdv-{rdvId}/paiement", method = RequestMethod.GET)
	public String paiementRendezVous(HttpServletRequest request, ModelMap map, @PathVariable String id,
			@PathVariable String rdvId) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			Patient patient = patientService.findById(id);
			RendezVous rdv = rendezVousService.getRepo().findOne(Long.parseLong(rdvId));

			map.put(ConstantesUtil.VIEW_TITLE, "Paiement");
			map.put(ConstantesUtil.URI, request.getRequestURI());
			map.put("patient", patient);
			map.put("rendezVous", rdv);
			map.put("typePaiement", TypeReglement.values());

			return "views/patient/paiement";
		}
	}

	@RequestMapping(value = "/home/patient-{id}/rdv-{rdvId}/paiement", method = RequestMethod.POST)
	public String paiementRendezVousPost(HttpServletRequest request, ModelMap map, @PathVariable String id,
			@PathVariable String rdvId, @RequestParam(name = "datePaiement") String datePaiement,
			@RequestParam(name = "montant") String montant, @RequestParam(name = "reglement") String reglement) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			Patient patient = patientService.findById(id);
			Praticien praticien = (Praticien) request.getSession().getAttribute(ConstantesUtil.ATTR_USER);
			RendezVous rdv = rendezVousService.getRepo().findOne(Long.parseLong(rdvId));
			Paiement paiement = null;

			try {
				paiement = new Paiement(ConstantesUtil.stringToDate(datePaiement), Float.valueOf(montant),
						TypeReglement.valueOf(reglement), rdv, praticien);
				paiementService.getRepo().save(paiement);
				rdv.setHasPaid(true);
				rendezVousService.getRepo().save(rdv);
			} catch (Exception e) {
				System.err.println("Erreur lors de l'enregitrement du paiement");
			}

			map.put(ConstantesUtil.URI, request.getRequestURI());
			map.put("patient", patient);
			map.put("rendezVous", rendezVousService.getRepo().findOne(Long.parseLong(rdvId)));

			if (paiement == null) {
				map.put(ConstantesUtil.VIEW_TITLE, "Paiement");
				map.put("typePaiement", TypeReglement.values());
				map.put("paiementNok", true);

				return "views/patient/paiement";
			} else {
				map.put(ConstantesUtil.VIEW_TITLE, "Rendez-vous");
				map.put("paiementOk", true);
				return "redirect:/home/patient-" + id + "/rdv-" + rdvId;
			}
		}
	}

	@RequestMapping(value = "/home/settings", method = RequestMethod.GET)
	public String indexPraticien(HttpServletRequest request, ModelMap map) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			map.put(ConstantesUtil.VIEW_TITLE, "Profil praticien");
			map.put("praticien", request.getSession().getAttribute(ConstantesUtil.ATTR_USER));

			return "views/praticien/settings";
		}
	}

	@RequestMapping(value = "/home/settings", method = RequestMethod.POST)
	public String postPraticien(HttpServletRequest request, ModelMap map,
			@RequestParam(name = "nom", required = false) String nom,
			@RequestParam(name = "prenom", required = false) String prenom,
			@RequestParam(name = "adresse", required = false) String adresse,
			@RequestParam(name = "ville", required = false) String ville,
			@RequestParam(name = "cp", required = false) String cp,
			@RequestParam(name = "siret", required = false) String siret,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "phone", required = false) String telephone,
			@RequestParam(name = "oldPassword", required = false) String oldPassword,
			@RequestParam(name = "newPassword", required = false) String newPassword) {
		if (request.getSession().getAttribute(ConstantesUtil.ATTR_USER) == null) {
			map.put(ConstantesUtil.VIEW_TITLE, "Login");
			return "redirect:/login";
		} else {
			Praticien praticien = (Praticien) request.getSession().getAttribute(ConstantesUtil.ATTR_USER);
			boolean pwdCheck = true;

			map.put(ConstantesUtil.VIEW_TITLE, "Profil praticien");
			// map.put(ConstantesUtil.URI, request.getRequestURI());

			praticien.setNom(nom);
			praticien.setPrenom(prenom);
			praticien.setAdresse(adresse);
			praticien.setVille(ville);
			praticien.setCodePostal(cp);
			praticien.setSiret(siret);
			praticien.setEmail(email);
			praticien.setTelephone(telephone);

			if (oldPassword != null && newPassword != null) {
				pwdCheck = authService.checkPassword(praticien.getPassword(), oldPassword);
				if (pwdCheck) {
					praticien.setPassword(authService.encodePassword(newPassword));
					authService.getRepo().save(praticien);
				}
			}

			map.put("pwdCheck", pwdCheck);
			map.put("praticien", praticien);

			request.getSession().setAttribute(ConstantesUtil.ATTR_USER, praticien);

			return "views/praticien/settings";
		}
	}
	
	@RequestMapping(value = "/home/dashboard", method = RequestMethod.GET)
	public String indexDashboard(HttpServletRequest request, ModelMap map) {
		Praticien praticien = (Praticien) request.getSession().getAttribute(ConstantesUtil.ATTR_USER);
		boolean queryOk = true;
		String msgError = "";
		List<Paiement> listePaiements = new ArrayList<>();
		List<Patient> listePatient = new ArrayList<>();
		List<BeanData> data = new ArrayList<>();
		
		try {
			msgError = "Erreur lors de la récupération des paiements";
			listePaiements = paiementService.getRepo().findByWeek();
			msgError = "Erreur lors de la récupération des statuts des rendez-vous";
			listePatient = patientService.getPatientRepository().findByPraticien(praticien);
			msgError = "Erreur lors de la génération de la collection";
			data = paiementService.trierRevenus(listePaiements);			
		} catch (Exception e) {
			System.err.println(msgError);
			queryOk = false;
		}
		
		map.put("praticien", praticien);
		map.put("queryOk", queryOk);
		map.put("dataRevenus", data);
		
		return "views/dashboard/index";
	}
}
