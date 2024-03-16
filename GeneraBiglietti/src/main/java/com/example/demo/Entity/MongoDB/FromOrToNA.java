package com.example.demo.Entity.MongoDB;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

/*
 * Descrive le tratte da e verso Napoli 
 * (fasce NA)
 */

@Component
public class FromOrToNA {

	@Id 
	private String id;
	
	private String stazione;
	private String fascia;
	
	public String getStazione() {
		return stazione;
	}
	public void setStazione(String stazione) {
		this.stazione = stazione;
	}
	public String getFascia() {
		return fascia;
	}
	public void setFascia(String fascia) {
		this.fascia = fascia;
	}
	
	
	
}
