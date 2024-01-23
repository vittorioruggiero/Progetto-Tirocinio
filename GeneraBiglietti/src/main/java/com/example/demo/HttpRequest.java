package com.example.demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

public class HttpRequest {

	private final RestTemplate restTemplate;
	
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
		
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		}
		else {
			throw new ResponseStatusException(response.getStatusCode(), "Errore nel recupero dei percorsi");
		}
		
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
		
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		}
		else {
			throw new ResponseStatusException(response.getStatusCode(), "Errore nel reverse geocoding");
		}
	}
	
}
