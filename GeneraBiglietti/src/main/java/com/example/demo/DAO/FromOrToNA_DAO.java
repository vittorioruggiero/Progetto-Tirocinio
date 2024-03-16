package com.example.demo.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.MongoDB.FromOrToNA;
import com.example.demo.Repository.FromOrToNARepository;

@Service
public class FromOrToNA_DAO {
	
	@Autowired
	private FromOrToNARepository repository;

	public FromOrToNA findByStazione(String stazione) {
		return repository.findByStazione(stazione);
	}
	
}
