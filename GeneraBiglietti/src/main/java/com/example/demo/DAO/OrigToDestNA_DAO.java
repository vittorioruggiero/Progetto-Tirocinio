package com.example.demo.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.MongoDB.OrigToDestNA;
import com.example.demo.Repository.OrigToDestNARepository;

@Service
public class OrigToDestNA_DAO {

	@Autowired
	private OrigToDestNARepository repository;

	public OrigToDestNA findByOrigineAndDestinazione(String origine, String destinazione) {
		return repository.findByOrigineAndDestinazione(origine, destinazione);
	}
	
}
