package com.atomika.gitByCity.dto.mapper;

import com.atomika.gitByCity.dto.PointOfInterestRoute;
import com.atomika.gitByCity.dto.Route;
import com.atomika.gitByCity.entity.AttachmentEntity;
import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.entity.RouteEntity;
import org.mapstruct.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", uses = {PointOfInterestRouteMapper.class, CommentMapper.class, ClientMapper.class, AttachmentMapper.class})
public interface RouteMapper {

    @Mapping(target = "pointsOfInterest", source = "pointOfInterestRoutesIds", qualifiedByName = "toPointOfInterestRouteList")
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "images", source = "images", qualifiedByName = "toAttachmentEntitiesRoute")
    RouteEntity dtoToEntity(Route route);

    @Mapping(target = "pointOfInterestRoutesIds", source = "pointsOfInterest", qualifiedByName = "toIdList")
    @Mapping(target = "likes", source = "likes", qualifiedByName = "toLikeListIds")
    @Mapping(target = "images", source = "images", qualifiedByName = "toImagesRoute")
    Route entityToDto(RouteEntity routeEntity);

    List<Route> toList(List<RouteEntity> routeEntityList);


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

    @Named("toImagesRoute")
    default List<String> toImagesRoute(List<AttachmentEntity> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }
        return images.stream()
                .map(AttachmentEntity::getImageUrl)
                .toList();
    }

    @Named("toAttachmentEntitiesRoute")
    default List<AttachmentEntity> toAttachmentEntitiesRoute(List<String> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }
        return images.stream().map(image -> {
            AttachmentEntity attachmentEntity = new AttachmentEntity();
            attachmentEntity.setImageUrl(image);
            attachmentEntity.setPointOfInterestId(null);
            return attachmentEntity;
        }).collect(Collectors.toList());
    }
}
