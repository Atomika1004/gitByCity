package com.atomika.gitByCity.repositories;

import com.atomika.gitByCity.entity.PointOfInterestRouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointOfInterestRouteRepository extends JpaRepository<PointOfInterestRouteEntity, Long> {

    List<PointOfInterestRouteEntity> findAllByOrderByPosition();

    List<PointOfInterestRouteEntity> findByPointOfInterestId(long pointOfInterestId);
}
