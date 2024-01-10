package com.example.demo.Utility;

import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONObject;

import com.example.demo.Entity.Leg;
import com.example.demo.Entity.ListaPercorsi;
import com.example.demo.Entity.Location;
import com.example.demo.Entity.Route;
import com.example.demo.Entity.Step;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//NON ANCORA COMPLETATA O USATA
public class Utility {
	
	private LinkedList<Step> listaTratteCampaniaProvvisoria = new LinkedList<Step>();
	private LinkedList<Step> listaTratteNonCoperteProvvisoria = new LinkedList<Step>();
	private ArrayList<Step> tratteCampania = new ArrayList<Step>();
	private ArrayList<Step> tratteNonCoperte = new ArrayList<Step>();

	public Utility() {
		
	}
	
	public String gestisciTratte(String percorsiJSON) {
		
		ListaPercorsi listaPercorsi = null;
		Route route;
		Leg leg;
		Step step;
		Location startLocation, endLocation;
		
		ArrayList<Route> routes;
		ArrayList<Leg> legs;
		ArrayList<Step> steps;
		
		String reverseGeocodedStartLocation;
		String reverseGeocodedEndLocation;
		
		try {
			
			
			listaPercorsi = new ObjectMapper().readValue(percorsiJSON, ListaPercorsi.class);
			
			routes = listaPercorsi.getRoutes();
			
			for(int i=0; i<routes.size(); i++) {
				
				route = routes.get(i);
				legs = route.getLegs();
				
				for(int j=0; j<legs.size(); j++) {
					
					leg = legs.get(j);
					steps = leg.getSteps();
					
					for(int k=0; k<steps.size(); k++) {
						
						step = steps.get(k);
						
						if(!(step.getTravel_mode()).equals("WALKING")) {
							
							startLocation = step.getStart_location();
							endLocation = step.getEnd_location();
							
							reverseGeocodedStartLocation = getLocationByCoordinates(startLocation.getLat(), startLocation.getLng());
							reverseGeocodedEndLocation = getLocationByCoordinates(endLocation.getLat(), endLocation.getLng());
							
							if(checkLocationIsInCampania(reverseGeocodedStartLocation) && checkLocationIsInCampania(reverseGeocodedEndLocation)) {
								System.out.println("\n");
								
								listaTratteCampaniaProvvisoria.add(step);
							}
							else {
								System.out.println("/n");
								
								listaTratteNonCoperteProvvisoria.add(step);
							}
						}
					}
					
					if(listaTratteCampaniaProvvisoria.size()>1) {
						tratteCampania.add(new Step(listaTratteCampaniaProvvisoria.getFirst().getStart_location(), listaTratteCampaniaProvvisoria.getLast().getEnd_location(), "TRANSIT"));
					}
					else if(!listaTratteCampaniaProvvisoria.isEmpty()) {
						tratteCampania.add(listaTratteCampaniaProvvisoria.getFirst());
					}
					listaTratteCampaniaProvvisoria.clear();
					
					if(listaTratteNonCoperteProvvisoria.size()>1) {
						tratteNonCoperte.add(new Step(listaTratteNonCoperteProvvisoria.getFirst().getStart_location(), listaTratteNonCoperteProvvisoria.getLast().getEnd_location(), "TRANSIT"));
					}
					else if(!listaTratteNonCoperteProvvisoria.isEmpty()) {
						tratteNonCoperte.add(listaTratteNonCoperteProvvisoria.getFirst());
					}
					listaTratteNonCoperteProvvisoria.clear();
					
				}
				
			}
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean checkLocationIsInCampania(String location) {
		
		JSONObject locationJSON = new JSONObject(location);
		
		return locationJSON.getString("principalSubdivision").equals("Campania");
		
	}
	
}
