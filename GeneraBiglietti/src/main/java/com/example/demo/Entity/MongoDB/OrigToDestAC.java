package com.example.demo.Entity.MongoDB;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

/*
 * Descrive le tratte che non passano attraverso Napoli 
 * con due comuni diversi come origine e destinazione 
 * (fasce AC)
 */

@Component
public class OrigToDestAC {

	@Id 
	private String id;
	
	private String origine;
	private String destinazione;
	private String fascia;

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public String getDestinazione() {
		return destinazione;
	}

	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}

	public String getFascia() {
		return fascia;
	}

	public void setFascia(String fascia) {
		this.fascia = fascia;
	}
	
}
