package com.atomika.gitByCity.dto.mapper;

import com.atomika.gitByCity.dto.PointOfInterestRoute;
import com.atomika.gitByCity.dto.Route;
import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.entity.RouteEntity;
import org.mapstruct.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Mapper(componentModel = "spring", uses = {PointOfInterestRouteMapper.class, CommentMapper.class, ClientMapper.class})
public interface RouteMapper {

    @Mapping(target = "pointsOfInterest", source = "pointOfInterestRoutesDto", qualifiedByName = "toPointOfInterestRouteList")
    @Mapping(target = "likes", ignore = true)
    RouteEntity dtoToEntity(Route route);

    @Mapping(target = "pointOfInterestRoutesDto", source = "pointsOfInterest", qualifiedByName = "toIdList")
    @Mapping(target = "likes", source = "likes", qualifiedByName = "toLikeListIds")
    Route entityToDto(RouteEntity routeEntity);

    List<Route> toList(List<RouteEntity> routeEntityList);

    List<RouteEntity> toListEntity(List<Route> routes);

    @Named("toIdList")
    default List<Long> toIdList(List<PointOfInterestRoute> pointOfInterestRoutes) {
        if (pointOfInterestRoutes == null || pointOfInterestRoutes.isEmpty()) {
            return null;
        }

        return pointOfInterestRoutes.stream().map(PointOfInterestRoute::getPointOfInterestId).toList();
    }

    @Named("toPointOfInterestRouteList")
    default List<PointOfInterestRoute> toPointOfInterestRouteList(List<Long> pointOfInterestIds) {
        if (pointOfInterestIds == null || pointOfInterestIds.isEmpty()) {
            return List.of();
        }
        AtomicInteger index = new AtomicInteger(1);
        return pointOfInterestIds.stream()
                .map(point -> PointOfInterestRoute.builder()
                        .pointOfInterestId(point)
                        .position(index.getAndIncrement())
                        .build())
                .toList();
    }

    @Named("toLikeListIds")
    default List<Long> toLikeListIds(List<ClientEntity> clientList) {
        if (clientList == null || clientList.isEmpty()) {
            return null;
        }
        return clientList.stream().map(ClientEntity::getId).toList();
    }
}
