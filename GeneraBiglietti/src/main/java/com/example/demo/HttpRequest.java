package com.example.demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Entity.Leg;
import com.example.demo.Entity.Location;
import com.example.demo.Entity.ListaPercorsi;
import com.example.demo.Entity.Route;
import com.example.demo.Entity.Step;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class HttpRequest {

	private final RestTemplate restTemplate;
	private LinkedList<Step> listaTratteCampaniaProvvisoria = new LinkedList<Step>();
	private LinkedList<Step> listaTratteNonCoperteProvvisoria = new LinkedList<Step>();
	private ArrayList<Step> listaTratteCampania = new ArrayList<Step>();
	private ArrayList<Step> listaTratteNonCoperte = new ArrayList<Step>();
	
	public HttpRequest() {
		
		this.restTemplate = new RestTemplate();
		
	}
	
	public String getPercorsi(String origine, String destinazione) {
		
		String directionsUrl = "https://maps.googleapis.com/maps/api/directions/json?"
				+ "origin=" + origine 
				+ "&destination=" + destinazione 
				+ "&key=AIzaSyAMIldziQUKMvnnWpa0YlQveklMuQC20Ag"
				+ "&alternatives=true&language=it&mode=transit&transit_mode=&transit_routing_preference=fewer_transfers";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("origine", origine);
		headers.set("destinazione", destinazione);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<String> response = this.restTemplate.exchange(directionsUrl, HttpMethod.GET, request, String.class);
		
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
			
			
			listaPercorsi = new ObjectMapper().readValue(response.getBody(), ListaPercorsi.class);
			
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
								listaTratteCampaniaProvvisoria.add(step);
							}
							else {
								listaTratteNonCoperteProvvisoria.add(step);
							}
						}
					}
					
					if(listaTratteCampaniaProvvisoria.size()>1) {
						listaTratteCampania.add(new Step(listaTratteCampaniaProvvisoria.getFirst().getStart_location(), listaTratteCampaniaProvvisoria.getLast().getEnd_location(), "TRANSIT"));
					}
					else if(!listaTratteCampaniaProvvisoria.isEmpty()) {
						listaTratteCampania.add(listaTratteCampaniaProvvisoria.getFirst());
					}
					listaTratteCampaniaProvvisoria.clear();
					
					if(listaTratteNonCoperteProvvisoria.size()>1) {
						listaTratteNonCoperte.add(new Step(listaTratteNonCoperteProvvisoria.getFirst().getStart_location(), listaTratteNonCoperteProvvisoria.getLast().getEnd_location(), "TRANSIT"));
					}
					else if(!listaTratteNonCoperteProvvisoria.isEmpty()) {
						listaTratteNonCoperte.add(listaTratteNonCoperteProvvisoria.getFirst());
					}
					listaTratteNonCoperteProvvisoria.clear();
					
				}
				
			}
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		//}
		//else
		
	}
	
	
	public String getLocationByCoordinates(String latitude, String longitude) {
		
		String reverseGeocodingUrl = "https://api.bigdatacloud.net/data/reverse-geocode-client?"
				+ "latitude=" + latitude
				+ "&longitude=" + longitude 
				+ "&localityLanguage=it";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("latitude", latitude);
		headers.set("longitude", longitude);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<String> response = this.restTemplate.exchange(reverseGeocodingUrl, HttpMethod.GET, request, String.class);
		
		return response.getBody();
	}
	
	public boolean checkLocationIsInCampania(String location) {
		
		JSONObject locationJSON = new JSONObject(location);
		
		return locationJSON.getString("principalSubdivision").equals("Campania");
		
	}
}
