package com.atomika.gitByCity.repositories;

import com.atomika.gitByCity.entity.PointOfInterestRouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointOfInterestRouteRepository extends JpaRepository<PointOfInterestRouteEntity, Long> {

    //List<PointOfInterestRouteEntity> findAll();

    List<PointOfInterestRouteEntity> findAllByOrderByPosition();

    List<PointOfInterestRouteEntity> findByPointOfInterestId(long pointOfInterestId);
}
