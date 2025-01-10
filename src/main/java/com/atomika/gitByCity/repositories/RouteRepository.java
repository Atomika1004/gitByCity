package com.atomika.gitByCity.repositories;


import com.atomika.gitByCity.entity.RouteEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RouteRepository extends JpaRepository<RouteEntity, Long > {


    @EntityGraph(attributePaths = "pointsOfInterest")
    List<RouteEntity> findAll();

    RouteEntity findById(long id);

    @Query("SELECT COUNT(r) > 0 FROM RouteEntity r WHERE r.client.credential.username = :username AND r.id = :routeId")
    boolean isCreator (String username, long routeId);
}
