package com.example.demo.Utility;

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
public class GeneratorePercorsi {
	
	private HttpRequest httpRequest;
	private LinkedList<Step> listaTratteCampaniaProvvisoria = new LinkedList<Step>();
	private LinkedList<Step> listaTratteNonCoperteProvvisoria = new LinkedList<Step>();
	private ArrayList<Step> listaTratteCampania = new ArrayList<Step>();
	private ArrayList<Step> listaTratteNonCoperte = new ArrayList<Step>();

	public GeneratorePercorsi() {
		
	}
	
	public String getPercorsi(String origine, String destinazione) {
		
		String percorsiJSON;
		ArrayList<Step> steps = null;
		ArrayList<Leg> legs = null;
		
		httpRequest = new HttpRequest();
		percorsiJSON = httpRequest.getPercorsi(origine, destinazione);
		
		legs = estraiLegs(percorsiJSON);
		
		for(Leg leg : legs) {
			
			steps = leg.getSteps();
			
			riempiListeProvvisorie(steps);
			unisciStep(listaTratteCampaniaProvvisoria, listaTratteCampania);
			unisciStep(listaTratteNonCoperteProvvisoria, listaTratteNonCoperte);
			
		}
		
		return percorsiJSON; /* temporaneo */
		
	}
	
	public ArrayList<Leg> estraiLegs(String percorsiJSON) {
		
		ListaPercorsi listaPercorsi = null;
		
		ArrayList<Route> routes;
		ArrayList<Leg> legs = null;
		
		try {
			
			listaPercorsi = new ObjectMapper().readValue(percorsiJSON, ListaPercorsi.class);
			
			routes = listaPercorsi.getRoutes();
			
			for(Route route : routes) {
				
				legs = route.getLegs();
				
			}
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return legs;
	}
	
	public void riempiListeProvvisorie(ArrayList<Step> steps) {
		
		Location startLocation, endLocation;
		String reverseGeocodedStartLocation, reverseGeocodedEndLocation;
		
		for(Step step : steps) {
			
			if(!(step.getTravel_mode()).equals("WALKING")) {
				
				startLocation = step.getStart_location();
				endLocation = step.getEnd_location();
				
				httpRequest = new HttpRequest();
				
				reverseGeocodedStartLocation = httpRequest.getLocationByCoordinates(startLocation.getLat(), startLocation.getLng());
				reverseGeocodedEndLocation = httpRequest.getLocationByCoordinates(endLocation.getLat(), endLocation.getLng());
				
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
			listaTratteFinale.add(new Step(listaTratteProvvisoria.getFirst().getStart_location(), listaTratteProvvisoria.getLast().getEnd_location(), "TRANSIT"));
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
