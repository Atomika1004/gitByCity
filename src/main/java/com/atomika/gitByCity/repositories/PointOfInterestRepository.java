package com.atomika.gitByCity.repositories;

import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.entity.PointOfInterestEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointOfInterestRepository extends CrudRepository<PointOfInterestEntity, Long> {

    @EntityGraph(attributePaths = {
            "likes"
    })
    List<PointOfInterestEntity> findAll();


    @Query("SELECT COUNT(p) > 0 FROM PointOfInterestEntity p WHERE p.client.credential.username = :username AND p.id = :pointOfInterestId ")
    public boolean isCreator (String username, long pointOfInterestId);
}
