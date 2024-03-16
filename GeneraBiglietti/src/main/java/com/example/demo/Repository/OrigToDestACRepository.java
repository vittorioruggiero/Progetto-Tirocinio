package com.example.demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.MongoDB.OrigToDestAC;

@Repository
@RepositoryRestResource(collectionResourceRel = "origToDestAC", path = "origToDestAC")
public interface OrigToDestACRepository extends MongoRepository<OrigToDestAC, String> {

	OrigToDestAC findByOrigineAndDestinazione(@Param("origine") String origine, @Param("destinazione") String destinazione);
	
}
