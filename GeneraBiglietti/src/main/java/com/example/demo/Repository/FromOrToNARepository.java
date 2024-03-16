package com.example.demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.MongoDB.FromOrToNA;

@Repository
@RepositoryRestResource(collectionResourceRel = "fromOrToNA", path = "fromOrToNA")
public interface FromOrToNARepository extends MongoRepository<FromOrToNA, String> {

	FromOrToNA findByStazione(@Param("stazione") String stazione);
	
}
