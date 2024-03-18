package com.example.demo.GeneratoreBiglietti;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.example.demo.DAO.Tariffa_DAO;
import com.example.demo.DAO.FromOrToNA_DAO;
import com.example.demo.DAO.OrigToDestAC_DAO;
import com.example.demo.DAO.OrigToDestNA_DAO;
import com.example.demo.Entity.JSON.Leg;
import com.example.demo.Entity.JSON.ListaRoutes;
import com.example.demo.Entity.JSON.Location;
import com.example.demo.Entity.JSON.Route;
import com.example.demo.Entity.JSON.Step;
import com.example.demo.Entity.MongoDB.FromOrToNA;
import com.example.demo.Entity.MongoDB.OrigToDestAC;
import com.example.demo.Entity.MongoDB.OrigToDestNA;
import com.example.demo.Entity.MongoDB.Tariffa;
import com.example.demo.HTTP.HttpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GeneratoreBiglietti {
	
	private static final String NAPOLI = "Napoli";
	private static final String AVELLINO = "Avellino";
	private static final String BENEVENTO = "Benevento";
	private static final String CASERTA = "Caaserta";
	private static final String SALERNO = "Salerno";
	
	private HttpRequest httpRequest;
	
	private LinkedList<Step> listaTratteCampaniaProvvisoria = new LinkedList<Step>();
	private LinkedList<Step> listaTratteFuoriCampaniaProvvisoria = new LinkedList<Step>();
	
	private List<Percorso> listaPercorsi = new ArrayList<>();
	
	private FromOrToNA_DAO fromOrToNA_DAO;
	private Tariffa_DAO tariffa_DAO;
	private OrigToDestAC_DAO origToDestAC_DAO;
	private OrigToDestNA_DAO origToDestNA_DAO;
	
	public GeneratoreBiglietti(FromOrToNA_DAO fromOrToNA_DAO, Tariffa_DAO tariffa_DAO, OrigToDestAC_DAO origToDestAC_DAO, OrigToDestNA_DAO origToDestNA_DAO) {
		this.fromOrToNA_DAO = fromOrToNA_DAO;
		this.tariffa_DAO = tariffa_DAO;
		this.origToDestAC_DAO = origToDestAC_DAO;
		this.origToDestNA_DAO = origToDestNA_DAO;
	}
	
	public List<ListaBigliettiPerTratta> generaBiglietti(String origine, String destinazione) {
		
		String percorsiJSON;
		List<Route> routes = null;
		List<Leg> legs = null;
		List<Step> steps = null;
		
		httpRequest = new HttpRequest();
		percorsiJSON = httpRequest.getPercorsi(origine, destinazione);
		
		routes = estraiRoutes(percorsiJSON);
		
		for(Route route : routes) {
			
			legs = route.getLegs();
			
			for(Leg leg : legs) {
				
				steps = leg.getSteps();
				
				riempiListeProvvisorie(steps);
				
				unisciStep();
				
			}
			
		}
		
		return convertiInBiglietti();
		
	}
	
	public List<Route> estraiRoutes(String percorsiJSON) {
		
		ListaRoutes listaRoutes = null;
		List<Route> routes = null;
		
		try {
			
			listaRoutes = new ObjectMapper().readValue(percorsiJSON, ListaRoutes.class);
			
			routes = listaRoutes.getRoutes();
			
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return routes;
		
	}
	
	public void riempiListeProvvisorie(List<Step> steps) {
		
		Location startLocation;
		Location endLocation;
		String reverseGeocodedStartLocation;
		String reverseGeocodedEndLocation;
		
		for(Step step : steps) {
			
			if(!(step.getTravelMode()).equals("WALKING")) {
				
				startLocation = step.getStartLocation();
				endLocation = step.getEndLocation();
				
				httpRequest = new HttpRequest();
				
				reverseGeocodedStartLocation = httpRequest.getLocationByCoordinates(startLocation.getLatitude(), startLocation.getLongitude());
				reverseGeocodedEndLocation = httpRequest.getLocationByCoordinates(endLocation.getLatitude(), endLocation.getLongitude());
				
				startLocation.setCityFromJSON(reverseGeocodedStartLocation);
				endLocation.setCityFromJSON(reverseGeocodedEndLocation);
				
				if(checkLocationIsInCampania(reverseGeocodedStartLocation) && checkLocationIsInCampania(reverseGeocodedEndLocation)) {
					if((startLocation.getCity()).equals(NAPOLI) || (endLocation.getCity()).equals(NAPOLI)) {
						step.setPassantePerNapoli(true);
					}
					listaTratteCampaniaProvvisoria.add(step);
				}
				else {
					listaTratteFuoriCampaniaProvvisoria.add(step);
				}
			}
			
		}
		
	}
	
	public boolean checkLocationIsInCampania(String locationJSON) {
		
		JSONObject locationJSONObject = new JSONObject(locationJSON);
		
		return locationJSONObject.getString("principalSubdivision").equals("Campania");
		
	}
	
	public void unisciStep() {
		
		Step trattaCampania;
		Step trattaFuoriCampania;
		
		trattaCampania = unisciTratteCampania();
		trattaFuoriCampania = unisciTratteFuoriCampania();
		
		listaPercorsi.add(new Percorso(trattaCampania, trattaFuoriCampania));
		
		listaTratteCampaniaProvvisoria.clear();
		listaTratteFuoriCampaniaProvvisoria.clear();
	}
	
	public Step unisciTratteCampania() {
		
		Step firstStep;
		Step lastStep;
		Step currentStep;
		Step trattaCampania = null;
		
		if(!listaTratteCampaniaProvvisoria.isEmpty()) {
			
			firstStep = listaTratteCampaniaProvvisoria.getFirst();
			lastStep = listaTratteCampaniaProvvisoria.getLast();
			
			if(listaTratteCampaniaProvvisoria.size()>1) {
				
				for(int i=0; i<listaTratteCampaniaProvvisoria.size(); i++) {
					
					currentStep = listaTratteCampaniaProvvisoria.get(i);
					
					if(!currentStep.isPassantePerNapoli()) {
						
						trattaCampania = new Step(firstStep.getStartLocation(), lastStep.getEndLocation(), "TRANSIT", true, false);
						
					}
					else if (checkPassaggioPerNapoli(currentStep, i)) {
								
								trattaCampania = new Step(firstStep.getStartLocation(), lastStep.getEndLocation(), "TRANSIT", true, true);
								
							}
				}
			}
			else if(listaTratteCampaniaProvvisoria.size()==1) {
				firstStep.setIntegrato(false);
				trattaCampania = firstStep;
			}
		}
		
		return trattaCampania;
		
	}
	
	public Step unisciTratteFuoriCampania() {
		
		Step firstStep;
		Step lastStep;
		Step trattaFuoriCampania = null;
		
		if(!listaTratteFuoriCampaniaProvvisoria.isEmpty()) {
			
			firstStep = listaTratteFuoriCampaniaProvvisoria.getFirst();
			lastStep = listaTratteFuoriCampaniaProvvisoria.getLast();
			
			if(listaTratteFuoriCampaniaProvvisoria.size()>1) {
				
				trattaFuoriCampania = new Step(firstStep.getStartLocation(), lastStep.getEndLocation(), "TRANSIT", true, false);
				
			}
			else if(listaTratteFuoriCampaniaProvvisoria.size()==1) {
				
				firstStep.setIntegrato(false);
				trattaFuoriCampania = firstStep;
				
			}
			
		}
		
		return trattaFuoriCampania;
		
	}
	
	
	public boolean checkPassaggioPerNapoli(Step currentStep, int i) {
		
		return (i==0 && (currentStep.getEndCity()).equals(NAPOLI)
		|| (i==listaTratteCampaniaProvvisoria.size()-1 && (currentStep.getStartCity()).equals(NAPOLI))
		|| (i!=0 && i !=listaTratteCampaniaProvvisoria.size()-1));
		
	}
	
	
	public List<ListaBigliettiPerTratta> convertiInBiglietti() {
		
		Step trattaCampania;
		Step trattaFuoriCampania;
		Biglietto bigliettoCampania = null;
		Biglietto bigliettoFuoriCampania = null;
		ListaBigliettiPerTratta listaBigliettiPerTratta = null;
		List<ListaBigliettiPerTratta> listaBiglietti = new ArrayList<>();
		
		for(Percorso percorso : listaPercorsi) {
			
			trattaCampania = percorso.getTrattaCampania();
			trattaFuoriCampania = percorso.getTrattaFuoriCampania();
			
			if(trattaCampania!=null) {
				bigliettoCampania = generaBigliettoCampania(trattaCampania);
			}
			else {
				bigliettoCampania = null;
			}
			
			if(trattaFuoriCampania!=null) {
				bigliettoFuoriCampania = new Biglietto(trattaFuoriCampania.getStartCity(), trattaFuoriCampania.getEndCity());
			}
			else {
				bigliettoFuoriCampania = null;
			}
			
			listaBigliettiPerTratta = new ListaBigliettiPerTratta(bigliettoCampania, bigliettoFuoriCampania);
			
			if(!listaBiglietti.contains(listaBigliettiPerTratta)) {
				listaBiglietti.add(listaBigliettiPerTratta);
			}
		}
		
		return listaBiglietti;
	}
	
	public Biglietto generaBigliettoCampania(Step trattaCampania) {
		
		String startCity;
		String endCity;
		Biglietto bigliettoCampania = null;
		
		startCity = trattaCampania.getStartCity();
		endCity = trattaCampania.getEndCity();
		
		//Fascia U NA (tipo A)
		if(startCity.equalsIgnoreCase(NAPOLI) && endCity.equalsIgnoreCase(NAPOLI)) {
			
			bigliettoCampania = generaBigliettoU_NA_A(startCity);
			
		}
		
		//Fascia U SA (tipo C)
		else if(startCity.equalsIgnoreCase(SALERNO) && endCity.equalsIgnoreCase(SALERNO)) {
			
			bigliettoCampania = generaBigliettoU_SA_C(startCity);
			
		}
		
		//Fasce U AV, U BN e U CE
		else if((startCity.equalsIgnoreCase(AVELLINO) && endCity.equalsIgnoreCase(AVELLINO))
				|| (startCity.equalsIgnoreCase(BENEVENTO) && endCity.equalsIgnoreCase(BENEVENTO))
				|| (startCity.equalsIgnoreCase(CASERTA) && endCity.equalsIgnoreCase(CASERTA))) {
			
			bigliettoCampania = generaBigliettoU_AV_BN_CE(startCity, endCity, trattaCampania);
			
		}
		
		//Fasce NA (da o per Napoli)
		else if(startCity.equalsIgnoreCase(NAPOLI) || endCity.equalsIgnoreCase(NAPOLI)) {
			
			if(startCity.equalsIgnoreCase(NAPOLI)) {
				bigliettoCampania = generaBigliettoFromNA(startCity, endCity, trattaCampania);
				
			}
			else {
				bigliettoCampania = generaBigliettoToNA(startCity, endCity, trattaCampania);
			}
		}
		
		//Fascia U AC
		else if(startCity.equalsIgnoreCase(endCity)) {
			bigliettoCampania = generaBigliettoU_AC(startCity);
		}
		
		//Fasce NA (attraverso Napoli) e AC
		else if(!startCity.equalsIgnoreCase(endCity)) {
			
			//Fasce NA (attraverso Napoli)
			if(trattaCampania.isPassantePerNapoli()) {
				bigliettoCampania = generaBigliettoOrigToDestNA(startCity, endCity, trattaCampania);
			}
			//Fasce AC
			else {
				bigliettoCampania = generaBigliettoAC(startCity, endCity, trattaCampania);
			}
		}
		
		return bigliettoCampania;
	}
	
	public Biglietto generaBigliettoU_AV_BN_CE(String startCity, String endCity, Step step) {
		
		Tariffa tariffa;
		Biglietto biglietto = new Biglietto();
		
		tariffa = tariffa_DAO.findByFascia("U_AV"); //Stessa tariffa di Benevento e Caserta
		
		if(startCity.equalsIgnoreCase(AVELLINO) && endCity.equalsIgnoreCase(AVELLINO))
			biglietto = new Biglietto(AVELLINO, AVELLINO, "U_AV");
		
		else if(startCity.equalsIgnoreCase(BENEVENTO) && endCity.equalsIgnoreCase(BENEVENTO))
			biglietto = new Biglietto(BENEVENTO, BENEVENTO, "U_BN");
		
		else if(startCity.equalsIgnoreCase(CASERTA) && endCity.equalsIgnoreCase(CASERTA))
			biglietto = new Biglietto(CASERTA, CASERTA, "U_CE");
		
		if(step.isIntegrato()) {
			biglietto.setPrezzo(tariffa.getIntegrato());
			biglietto.setIntegrato(true);
		}
		else {
			biglietto.setPrezzo(tariffa.getAziendale());
		}
		
		
		return biglietto;
	}
	
	public Biglietto generaBigliettoFromNA(String startCity, String endCity, Step step) {
		
		FromOrToNA fromOrToNA;
		Tariffa tariffa;
		Biglietto biglietto = null;
		
		fromOrToNA = fromOrToNA_DAO.findByStazione(endCity.toUpperCase());
		
		if(fromOrToNA!=null) {
			
			tariffa = tariffa_DAO.findByFascia("NA_" + fromOrToNA.getFascia());
			
			biglietto = new Biglietto(NAPOLI, endCity, tariffa.getFascia());
			
			if(step.isIntegrato()) {
				biglietto.setPrezzo(tariffa.getIntegrato());
				biglietto.setIntegrato(true);
			}
			else {
				biglietto.setPrezzo(tariffa.getAziendale());
			}
			
		}
		
		return biglietto;
	}
	
	public Biglietto generaBigliettoToNA(String startCity, String endCity, Step step) {
		
		FromOrToNA fromOrToNA;
		Tariffa tariffa;
		Biglietto biglietto = null;
		
		fromOrToNA = fromOrToNA_DAO.findByStazione(startCity.toUpperCase());
		
		if(fromOrToNA!=null) {
			
			tariffa = tariffa_DAO.findByFascia("NA_" + fromOrToNA.getFascia());
			
			biglietto = new Biglietto(startCity, NAPOLI, tariffa.getFascia());
			
			if(step.isIntegrato()) {
				biglietto.setPrezzo(tariffa.getIntegrato());
				biglietto.setIntegrato(true);
			}
			else {
				biglietto.setPrezzo(tariffa.getAziendale());
			}
			
		}
		
		return biglietto;
	}
	
	public Biglietto generaBigliettoU_NA_A(String startCity) {
		
		Tariffa tariffa;
		
		tariffa = tariffa_DAO.findByFasciaAndTipo("U_NA", "A");
		
		return new Biglietto(startCity, startCity, "U_NA", tariffa.getAziendale(), false); //Non ha integrato
	}
	
	public Biglietto generaBigliettoU_SA_C(String startCity) {
		
		Tariffa tariffa;
		
		tariffa = tariffa_DAO.findByFasciaAndTipo("U_SA", "C");
		
		return new Biglietto(startCity, startCity, "U_SA", tariffa.getAziendale(), false); //Non ha integrato
	}
	
	public Biglietto generaBigliettoU_AC(String startCity) {
		
		Tariffa tariffa;
		
		tariffa = tariffa_DAO.findByFascia("U_AC");
		
		return new Biglietto(startCity, startCity, "U_AC", tariffa.getAziendale(), false); //Non ha integrato
	}
	
	public Biglietto generaBigliettoAC(String startCity, String endCity, Step step) {
		
		OrigToDestAC origToDestAC;
		Tariffa tariffa;
		Biglietto biglietto = null;
		
		origToDestAC = origToDestAC_DAO.findByOrigineAndDestinazione(startCity.toUpperCase(), endCity.toUpperCase());
		
		if(origToDestAC!=null) {
			
			tariffa = tariffa_DAO.findByFascia("AC_" + origToDestAC.getFascia());
			
			biglietto = new Biglietto(startCity, endCity, tariffa.getFascia());
			
			if(step.isIntegrato()) {
				biglietto.setPrezzo(tariffa.getIntegrato());
				biglietto.setIntegrato(true);
			}
			else {
				biglietto.setPrezzo(tariffa.getAziendale());
			}
			
		}
		
		return biglietto;
		
	}
	
public Biglietto generaBigliettoOrigToDestNA(String startCity, String endCity, Step step) {
		
		OrigToDestNA origToDestNA;
		Tariffa tariffa;
		Biglietto biglietto = null;
		
		origToDestNA = origToDestNA_DAO.findByOrigineAndDestinazione(startCity.toUpperCase(), endCity.toUpperCase());
		
		if(origToDestNA!=null) {
			
			tariffa = tariffa_DAO.findByFascia("NA_" + origToDestNA.getFascia());
			
			biglietto = new Biglietto(startCity, endCity, tariffa.getFascia());
			
			if(step.isIntegrato()) {
				biglietto.setPrezzo(tariffa.getIntegrato());
				biglietto.setIntegrato(true);
			}
			else {
				biglietto.setPrezzo(tariffa.getAziendale());
			}
			
		}
		
		return biglietto;
		
	}
	
}
