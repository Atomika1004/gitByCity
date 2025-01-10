package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.PointOfInterestRoute;
import com.atomika.gitByCity.dto.mapper.PointOfInterestRouteMapper;
import com.atomika.gitByCity.repositories.PointOfInterestRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointOfInterestRouteService {

    private final PointOfInterestRouteRepository pointOfInterestRouteRepository;
    private final PointOfInterestRouteMapper pointOfInterestRouteMapper;

    public PointOfInterestRoute create (PointOfInterestRoute pointOfInterestRoute) {
        return pointOfInterestRouteMapper.entityToDto(
                pointOfInterestRouteRepository.save(pointOfInterestRouteMapper.dtoToEntity(pointOfInterestRoute)));
    }

    public PointOfInterestRoute update (PointOfInterestRoute pointOfInterestRoute) {
        return pointOfInterestRouteMapper.entityToDto(
                pointOfInterestRouteRepository.save(pointOfInterestRouteMapper.dtoToEntity(pointOfInterestRoute)));
    }

    public Long delete (Long id) {
        pointOfInterestRouteRepository.deleteById(id);
        return id;
    }

    public List<PointOfInterestRoute> findAll() {
        return pointOfInterestRouteMapper.toList(pointOfInterestRouteRepository.findAllByOrderByPosition());
    }

    public List<PointOfInterestRoute> saveAll (List<PointOfInterestRoute> pointOfInterestRoutes) {
        return pointOfInterestRouteMapper.toList((List<com.atomika.gitByCity.entity.PointOfInterestRouteEntity>) pointOfInterestRouteRepository.saveAll(pointOfInterestRouteMapper.toListEntity(pointOfInterestRoutes)));
    }
}
