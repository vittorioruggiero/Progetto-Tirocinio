package com.example.demo.Entity.JSON;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

	@JsonProperty("lat")
	private String latitude;
	@JsonProperty("lng")
	private String longitude;
	private String city;
	
	public Location() {
		
	}
	
	public Location(String latitude, String longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public void setCityFromJSON(String locationJSON) {
		
		JSONObject locationJSONObject = new JSONObject(locationJSON);
		
		setCity(locationJSONObject.getString("city"));
		
	}
	
}
