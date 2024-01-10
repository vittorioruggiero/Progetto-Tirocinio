package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneraPercorsiController {

	
	@GetMapping("/generaBiglietti/getPercorsi")
		public String getPercorsi(@RequestParam("origine") String origine,
					                @RequestParam("destinazione") String destinazione) {
		
		String percorsiJSON;
		HttpRequest request = new HttpRequest();
		
		percorsiJSON = request.getPercorsi(origine, destinazione);
		
		// utility.gestisciTratte(percorsiJSON);
		
		return percorsiJSON;
	}
}
