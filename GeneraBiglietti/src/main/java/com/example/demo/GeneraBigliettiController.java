package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DAO.FromOrToNA_DAO;
import com.example.demo.DAO.OrigToDestAC_DAO;
import com.example.demo.DAO.OrigToDestNA_DAO;
import com.example.demo.DAO.Tariffa_DAO;
import com.example.demo.GeneratoreBiglietti.GeneratoreBiglietti;
import com.example.demo.GeneratoreBiglietti.ListaBigliettiPerTratta;

@RestController
public class GeneraBigliettiController {

	
	
	@Autowired
	private FromOrToNA_DAO fromOrToNA_DAO;
	@Autowired
	private Tariffa_DAO tariffa_DAO;
	@Autowired
	private OrigToDestAC_DAO origToDestAC_DAO;
	@Autowired
	private OrigToDestNA_DAO origToDestNA_DAO;
	
	@GetMapping("/generaBiglietti")
		public List<ListaBigliettiPerTratta> getBiglietti(@RequestParam("origine") String origine,
					                @RequestParam("destinazione") String destinazione) {
		
  GeneratoreBiglietti generatoreBiglietti;
		
		generatoreBiglietti = new GeneratoreBiglietti(fromOrToNA_DAO, tariffa_DAO, origToDestAC_DAO, origToDestNA_DAO);
		return generatoreBiglietti.generaBiglietti(origine, destinazione);
		
	}
}
