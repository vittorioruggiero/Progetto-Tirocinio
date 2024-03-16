package com.example.demo.GeneratoreBiglietti;

import com.example.demo.Entity.JSON.Step;

public class Percorso {

	private Step trattaCampania;
	private Step trattaFuoriCampania;
	
	public Percorso(Step trattaCampania, Step trattaFuoriCampania) {
		super();
		this.trattaCampania = trattaCampania;
		this.trattaFuoriCampania = trattaFuoriCampania;
	}

	public Step getTrattaCampania() {
		return trattaCampania;
	}

	public void setTrattaCampania(Step trattaCampania) {
		this.trattaCampania = trattaCampania;
	}

	public Step getTrattaFuoriCampania() {
		return trattaFuoriCampania;
	}

	public void setTrattaFuoriCampania(Step trattaFuoriCampania) {
		this.trattaFuoriCampania = trattaFuoriCampania;
	}
	
}
