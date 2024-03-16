package com.example.demo.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.MongoDB.OrigToDestAC;
import com.example.demo.Repository.OrigToDestACRepository;

@Service
public class OrigToDestAC_DAO {

	@Autowired
	private OrigToDestACRepository repository;

	public OrigToDestAC findByOrigineAndDestinazione(String origine, String destinazione) {
		return repository.findByOrigineAndDestinazione(origine, destinazione);
	}
}
