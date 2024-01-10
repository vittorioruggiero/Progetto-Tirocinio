package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Step {

	private Location start_location;
	private Location end_location;
	private String travel_mode;
	
	public Step() {
		
	}
	
	public Step(Location startLocation, Location endLocation, String travelMode) {
		super();
		this.start_location = startLocation;
		this.end_location = endLocation;
		this.travel_mode = travelMode;
	}

	public Location getStart_location() {
		return start_location;
	}

	public void setStart_location(Location startLocation) {
		this.start_location = startLocation;
	}

	public Location getEnd_location() {
		return end_location;
	}

	public void setEnd_location(Location endLocation) {
		this.end_location = endLocation;
	}

	public String getTravel_mode() {
		return travel_mode;
	}

	public void setTravel_mode(String travel_mode) {
		this.travel_mode = travel_mode;
	}
	
}
