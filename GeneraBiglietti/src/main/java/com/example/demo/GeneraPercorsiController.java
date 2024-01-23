package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.GeneratoreBiglietti.GeneratoreBiglietti;

@RestController
public class GeneraPercorsiController {

	
	@GetMapping("/generaBiglietti")
		public String getPercorsi(@RequestParam("origine") String origine,
					                @RequestParam("destinazione") String destinazione) {
		
		GeneratoreBiglietti generatoreBiglietti = new GeneratoreBiglietti();
		
		return generatoreBiglietti.generaBiglietti(origine, destinazione);
		
	}
}
