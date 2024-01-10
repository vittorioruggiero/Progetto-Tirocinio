package com.example.demo.Entity;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Leg {

	private ArrayList<Step> steps;
	
	public Leg() {
		
	}

	public Leg(ArrayList<Step> steps) {
		super();
		this.steps = steps;
	}

	public ArrayList<Step> getSteps() {
		return steps;
	}

	public void setSteps(ArrayList<Step> steps) {
		this.steps = steps;
	}
	
}
