package com.example.demo.GeneratoreBiglietti;

import java.util.Objects;

public class ListaBigliettiPerTratta {

	private Biglietto bigliettoAcquistabile;
	private Biglietto bigliettoNonAcquistabile;
	
	public ListaBigliettiPerTratta(Biglietto bigliettoAcquistabile, Biglietto bigliettoNonAcquistabile) {
		super();
		this.bigliettoAcquistabile = bigliettoAcquistabile;
		this.bigliettoNonAcquistabile = bigliettoNonAcquistabile;
	}

	public Biglietto getBigliettoAcquistabile() {
		return bigliettoAcquistabile;
	}

	public void setBigliettoAcquistabile(Biglietto bigliettoAcquistabile) {
		this.bigliettoAcquistabile = bigliettoAcquistabile;
	}

	public Biglietto getBigliettoNonAcquistabile() {
		return bigliettoNonAcquistabile;
	}

	public void setBigliettoNonAcquistabile(Biglietto bigliettoNonAcquistabile) {
		this.bigliettoNonAcquistabile = bigliettoNonAcquistabile;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj == this)
			return true;
		
		if(!(obj instanceof ListaBigliettiPerTratta))
			return false;
		
		ListaBigliettiPerTratta listaBigliettiPerTratta = (ListaBigliettiPerTratta) obj;
		
		return (Objects.equals(bigliettoAcquistabile, listaBigliettiPerTratta.getBigliettoAcquistabile())
		&& Objects.equals(bigliettoNonAcquistabile, listaBigliettiPerTratta.getBigliettoNonAcquistabile()));
	}
	
}
