package com.atomika.gitByCity.dto.mapper;


import com.atomika.gitByCity.entity.PointOfInterestRouteEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ListPointOfInterestMapper {

    //List<Long> toPointOfInterestDto(List<PointOfInterestRouteEntity> pointOfInterestRouteEntityList);
}
