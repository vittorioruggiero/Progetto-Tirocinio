package com.example.demo.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.MongoDB.Tariffa;
import com.example.demo.Repository.TariffaRepository;

@Service
public class Tariffa_DAO {

	@Autowired
	private TariffaRepository repository;

	public Tariffa findByFascia(String fascia) {
		return repository.findByFascia(fascia);
	}
	
	public Tariffa findByFasciaAndTipo(String fascia, String tipo) {
		return repository.findByFasciaAndTipo(fascia, tipo);
	}
	
}
