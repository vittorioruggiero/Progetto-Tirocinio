package com.example.demo.Entity.MongoDB;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class Tariffa {

	@Id
	private String id;
	
	private String fascia;
	private String tipo;
	private Double aziendale;
	private Double integrato;
	
	public String getFascia() {
		return fascia;
	}
	public void setFascia(String fascia) {
		this.fascia = fascia;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Double getAziendale() {
		return aziendale;
	}
	public void setAziendale(Double aziendale) {
		this.aziendale = aziendale;
	}
	public Double getIntegrato() {
		return integrato;
	}
	public void setIntegrato(Double integrato) {
		this.integrato = integrato;
	}
	
	
}
