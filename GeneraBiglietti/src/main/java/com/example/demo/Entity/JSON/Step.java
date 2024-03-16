package com.example.demo.Entity.JSON;

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
	private boolean integrato;
	private boolean passantePerNapoli; //Deve essere true quando origine o destinazione sono Napoli, oppure quando lo step è il risultato di un unione di altri passanti per Napoli
	
	public Step() {
		
	}
	
	public Step(Location startLocation, Location endLocation, String travelMode, boolean integrato) {
		super();
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.travelMode = travelMode;
		this.integrato = integrato;
	}
	
	public Step(Location startLocation, Location endLocation, String travelMode, boolean integrato, boolean passantePerNapoli) {
		super();
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.travelMode = travelMode;
		this.integrato = integrato;
		this.passantePerNapoli = passantePerNapoli;
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

	public boolean isIntegrato() {
		return integrato;
	}

	public void setIntegrato(boolean integrato) {
		this.integrato = integrato;
	}
	
	public String getStartCity() {
		return startLocation.getCity();
	}
	
	public String getEndCity() {
		return endLocation.getCity();
	}

	public boolean isPassantePerNapoli() {
		return passantePerNapoli;
	}

	public void setPassantePerNapoli(boolean passantePerNapoli) {
		this.passantePerNapoli = passantePerNapoli;
	}
	
}
