package com.atomika.gitByCity.dto.mapper;

import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.entity.PointOfInterestEntity;
import com.atomika.gitByCity.entity.RouteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

//    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
    @Mapping(target = "credential", ignore = true)
//    @Mapping(target = "likedRoutes", ignore = true)
//    @Mapping(target = "likedPoints", ignore = true)
    ClientEntity dtoToEntity (Client client);

    @Mapping(target = "credential", ignore = true)
//    @Mapping(target = "likedRoutes", source = "likedRoutes", qualifiedByName = "toLikeListIdsForRoutes")
//    @Mapping(target = "likedPoints", source = "likedPoints", qualifiedByName = "toLikeListIdsForPoints")
    Client entityToDto(ClientEntity clientEntity);

    List<Client> toList(List<ClientEntity> clientEntityList);

//    @Named("toLikeListIdsForRoutes")
//    default List<Long> toLikeListIdsForRoutes(List<RouteEntity> routeEntities) {
//        if (routeEntities == null || routeEntities.isEmpty()) {
//            return null;
//        }
//        return routeEntities.stream().map(RouteEntity::getClientId).toList();
//    }
//
//    @Named("toLikeListIdsForPoints")
//    default List<Long> toLikeListIdsForPoints(List<PointOfInterestEntity> pointOfInterestEntities) {
//        if (pointOfInterestEntities == null || pointOfInterestEntities.isEmpty()) {
//            return null;
//        }
//        return pointOfInterestEntities.stream().map(PointOfInterestEntity::getClientId).toList();
//    }

}
