package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

	private String lat;
	private String lng;
	
	public Location() {
		
	}
	
	public Location(String latitude, String longitude) {
		super();
		this.lat = latitude;
		this.lng = longitude;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String latitude) {
		this.lat = latitude;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String longitude) {
		this.lng = longitude;
	}
	
}
