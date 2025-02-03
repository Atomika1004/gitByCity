package com.atomika.gitByCity.dto.mapper;

import com.atomika.gitByCity.dto.PointOfInterestRoute;
import com.atomika.gitByCity.entity.PointOfInterestRouteEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface PointOfInterestRouteMapper {

    PointOfInterestRouteEntity dtoToEntity (PointOfInterestRoute pointOfInterestRoute);

    PointOfInterestRoute entityToDto(PointOfInterestRouteEntity pointOfInterestRouteEntity);

    List<PointOfInterestRoute> toList(List<PointOfInterestRouteEntity> pointOfInterestRouteEntityList);

    List<PointOfInterestRouteEntity> toListEntity(List<PointOfInterestRoute> pointOfInterestRoutesDto);
}
