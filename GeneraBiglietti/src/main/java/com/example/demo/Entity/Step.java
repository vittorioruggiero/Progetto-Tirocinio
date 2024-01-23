package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Step {

	@JsonProperty("start_location")
	private Location startLocation;
	@JsonProperty("end_location")
	private Location endLocation;
	@JsonProperty("travel_mode")
	private String travelMode;
	
	public Step() {
		
	}
	
	public Step(Location startLocation, Location endLocation, String travelMode) {
		super();
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.travelMode = travelMode;
	}

	public Location getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
	}

	public Location getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(Location endLocation) {
		this.endLocation = endLocation;
	}

	public String getTravelMode() {
		return travelMode;
	}

	public void setTravelMode(String travel_mode) {
		this.travelMode = travel_mode;
	}
	
}
