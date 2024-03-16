package com.example.demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.MongoDB.OrigToDestNA;

@Repository
@RepositoryRestResource(collectionResourceRel = "origToDestNA", path = "origToDestNA")
public interface OrigToDestNARepository extends MongoRepository<OrigToDestNA, String> {
	
	OrigToDestNA findByOrigineAndDestinazione(@Param("origine") String origine, @Param("destinazione") String destinazione);
	
}
