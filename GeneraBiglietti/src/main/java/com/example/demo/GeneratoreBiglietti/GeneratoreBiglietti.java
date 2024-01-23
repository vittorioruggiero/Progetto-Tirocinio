package com.example.demo.GeneratoreBiglietti;

import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONObject;

import com.example.demo.HttpRequest;
import com.example.demo.Entity.Leg;
import com.example.demo.Entity.ListaPercorsi;
import com.example.demo.Entity.Location;
import com.example.demo.Entity.Route;
import com.example.demo.Entity.Step;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//NON ANCORA COMPLETATA O USATA
public class GeneratoreBiglietti {
	
	private HttpRequest httpRequest;
	private LinkedList<Step> listaTratteCampaniaProvvisoria = new LinkedList<Step>();
	private LinkedList<Step> listaTratteNonCoperteProvvisoria = new LinkedList<Step>();
	private ArrayList<Step> listaTratteCampania = new ArrayList<Step>();
	private ArrayList<Step> listaTratteNonCoperte = new ArrayList<Step>();

	public GeneratoreBiglietti() {
		
	}
	
	public String generaBiglietti(String origine, String destinazione) {
		
		String percorsiJSON;
		ArrayList<Route> routes = null;
		ArrayList<Leg> legs = null;
		ArrayList<Step> steps = null;
		
		httpRequest = new HttpRequest();
		percorsiJSON = httpRequest.getPercorsi(origine, destinazione);
		
		routes = estraiRoutes(percorsiJSON);
		
		for(Route route : routes) {
			
			legs = route.getLegs();
			
			for(Leg leg : legs) {
				
				steps = leg.getSteps();
				
				riempiListeProvvisorie(steps);
				unisciStep(listaTratteCampaniaProvvisoria, listaTratteCampania);
				unisciStep(listaTratteNonCoperteProvvisoria, listaTratteNonCoperte);
				
			}
			
		}
		
		System.out.println("\nLista tratte Campania: ");
		
		for(Step step : listaTratteCampania) {
			
			System.out.println("\nstart location: \nlat: " + step.getStartLocation().getLatitude() + "\nlng: " + step.getStartLocation().getLongitude());
			System.out.println("\nend location: \nlat: " + step.getEndLocation().getLatitude() + "\nlng: " + step.getEndLocation().getLongitude());
			
		}
		
		System.out.println("\nLista tratte non coperte: ");
		
		for(Step step : listaTratteNonCoperte) {
			
			System.out.println("\nstart location: \nlat: " + step.getStartLocation().getLatitude() + "\nlng: " + step.getStartLocation().getLongitude());
			System.out.println("\nend location: \nlat: " + step.getEndLocation().getLatitude() + "\nlng: " + step.getEndLocation().getLongitude());
			
		}
		
		return percorsiJSON; /* temporaneo */
		
	}
	
	public ArrayList<Route> estraiRoutes(String percorsiJSON) {
		
		ListaPercorsi listaPercorsi = null;
		ArrayList<Route> routes = null;
		
		try {
			
			listaPercorsi = new ObjectMapper().readValue(percorsiJSON, ListaPercorsi.class);
			
			routes = listaPercorsi.getRoutes();
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return routes;
		
	}
	
	public void riempiListeProvvisorie(ArrayList<Step> steps) {
		
		Location startLocation, endLocation;
		String reverseGeocodedStartLocation, reverseGeocodedEndLocation;
		
		for(Step step : steps) {
			
			if(!(step.getTravelMode()).equals("WALKING")) {
				
				startLocation = step.getStartLocation();
				endLocation = step.getEndLocation();
				
				httpRequest = new HttpRequest();
				
				reverseGeocodedStartLocation = httpRequest.getLocationByCoordinates(startLocation.getLatitude(), startLocation.getLongitude());
				reverseGeocodedEndLocation = httpRequest.getLocationByCoordinates(endLocation.getLatitude(), endLocation.getLongitude());
				
				if(checkLocationIsInCampania(reverseGeocodedStartLocation) && checkLocationIsInCampania(reverseGeocodedEndLocation)) {
					listaTratteCampaniaProvvisoria.add(step);
				}
				else {
					listaTratteNonCoperteProvvisoria.add(step);
				}
			}
			
		}
		
	}
	
	public void unisciStep(LinkedList<Step> listaTratteProvvisoria, ArrayList<Step> listaTratteFinale) {
		
		if(listaTratteProvvisoria.size()>1) {
			listaTratteFinale.add(new Step(listaTratteProvvisoria.getFirst().getStartLocation(), listaTratteProvvisoria.getLast().getEndLocation(), "TRANSIT"));
		}
		else if(!listaTratteProvvisoria.isEmpty()) {
			listaTratteFinale.add(listaTratteProvvisoria.getFirst());
		}
		listaTratteProvvisoria.clear();
		
	}
	
	public boolean checkLocationIsInCampania(String location) {
		
		JSONObject locationJSON = new JSONObject(location);
		
		return locationJSON.getString("principalSubdivision").equals("Campania");
		
	}
	
}
