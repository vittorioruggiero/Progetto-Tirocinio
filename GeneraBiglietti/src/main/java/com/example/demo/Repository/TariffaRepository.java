package com.example.demo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.MongoDB.Tariffa;

@Repository
@RepositoryRestResource(collectionResourceRel = "tariffa", path = "tariffa")
public interface TariffaRepository extends MongoRepository<Tariffa, String> {

	Tariffa findByFascia(@Param("fascia") String fascia);
	Tariffa findByFasciaAndTipo(@Param("fascia") String fascia, @Param("tipo") String tipo);
}
