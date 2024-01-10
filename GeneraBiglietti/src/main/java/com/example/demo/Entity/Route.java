package com.example.demo.Entity;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {

	private ArrayList<Leg> legs;

	public Route() {
		
	}
	
	public Route(ArrayList<Leg> legs) {
		super();
		this.legs = legs;
	}

	public ArrayList<Leg> getLegs() {
		return legs;
	}

	public void setLegs(ArrayList<Leg> legs) {
		this.legs = legs;
	}
	
}
