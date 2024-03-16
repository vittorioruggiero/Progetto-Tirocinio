package com.example.demo.Entity.JSON;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {

	private List<Leg> legs;

	public Route() {
		
	}
	
	public Route(List<Leg> legs) {
		super();
		this.legs = legs;
	}

	public List<Leg> getLegs() {
		return legs;
	}

	public void setLegs(List<Leg> legs) {
		this.legs = legs;
	}
	
}
