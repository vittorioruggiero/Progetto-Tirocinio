package com.example.demo.Entity.JSON;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Leg {

	private List<Step> steps;
	
	public Leg() {
		
	}

	public Leg(List<Step> steps) {
		super();
		this.steps = steps;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
	
}
