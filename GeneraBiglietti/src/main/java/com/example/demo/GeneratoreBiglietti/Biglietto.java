package com.example.demo.GeneratoreBiglietti;

import java.util.Objects;

public class Biglietto {

	private String origine;
	private String destinazione;
	private String fascia;
	private Double prezzo;
	private boolean integrato;
	
	public Biglietto() {
		super();
	}
	
	public Biglietto(String origine, String destinazione) {
		super();
		this.origine = origine;
		this.destinazione = destinazione;
	}
	
	public Biglietto(String origine, String destinazione, String fascia) {
		super();
		this.origine = origine;
		this.destinazione = destinazione;
		this.fascia = fascia;
	}

	public Biglietto(String origine, String destinazione, String fascia, Double prezzo, boolean integrato) {
		super();
		this.origine = origine;
		this.destinazione = destinazione;
		this.fascia = fascia;
		this.prezzo = prezzo;
		this.integrato = integrato;
	}
	
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
	public Double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public boolean isIntegrato() {
		return integrato;
	}

	public void setIntegrato(boolean integrato) {
		this.integrato = integrato;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj == this)
			return true;
		
		if(!(obj instanceof Biglietto))
			return false;
		
		Biglietto biglietto = (Biglietto) obj;
		
		return (origine.equals(biglietto.getOrigine())
		&& destinazione.equals(biglietto.getDestinazione())
		&& Objects.equals(fascia, biglietto.getFascia())
		&& Objects.equals(prezzo, biglietto.getPrezzo())
		&& integrato==biglietto.isIntegrato());
		
	}
	
}
