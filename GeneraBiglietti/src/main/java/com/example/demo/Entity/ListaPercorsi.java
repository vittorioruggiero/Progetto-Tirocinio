package com.example.demo.Entity;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListaPercorsi {

	private ArrayList<Route> routes;

	public ListaPercorsi() {
		
	}
	
	public ListaPercorsi(ArrayList<Route> routes) {
		super();
		this.routes = routes;
	}

	public ArrayList<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(ArrayList<Route> routes) {
		this.routes = routes;
	}
	
}
