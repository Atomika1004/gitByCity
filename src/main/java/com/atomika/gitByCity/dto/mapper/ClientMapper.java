package com.atomika.gitByCity.dto.mapper;

import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.dto.profile.PointForProfile;
import com.atomika.gitByCity.dto.profile.RouteForProfile;
import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.entity.CredentialEntity;
import com.atomika.gitByCity.entity.PointOfInterestEntity;
import com.atomika.gitByCity.entity.RouteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "credential", ignore = true)
    ClientEntity dtoToEntity (Client client);

    @Mapping(target = "createdRoute", source = "route", qualifiedByName = "toCreatedRoute")
    @Mapping(target = "estimatedRoute", source = "likedRoutes", qualifiedByName = "toEstimatedRoute")
    @Mapping(target = "createdPointOfInterest", source = "pointOfInterest", qualifiedByName = "toCreatedPoint")
    @Mapping(target = "estimatedPointOfInterest", source = "likedPoints", qualifiedByName = "toEstimatedPoint")
    @Mapping(target = "email", source = "credential", qualifiedByName = "toEmail")
    Client entityToDto(ClientEntity clientEntity);

    List<Client> toList(List<ClientEntity> clientEntityList);


    @Named("toCreatedRoute")
    default Set<RouteForProfile> toCreatedRoute(Set<RouteEntity> routeEntitySet) {
        if (routeEntitySet == null || routeEntitySet.isEmpty()) {
            return null;
        }

        return routeEntitySet.stream()
                .map(routeEntity -> RouteForProfile.builder()
                        .id(routeEntity.getId())
                        .name(routeEntity.getName())
                        .description(routeEntity.getDescription())
                        .build()).collect(Collectors.toSet());

    }

    @Named("toEstimatedRoute")
    default Set<RouteForProfile> toEstimatedRoute(Set<RouteEntity> routeEntitySet) {
        if (routeEntitySet == null || routeEntitySet.isEmpty()) {
            return null;
        }

        return routeEntitySet.stream()
                .map(routeEntity -> RouteForProfile.builder()
                        .id(routeEntity.getId())
                        .name(routeEntity.getName())
                        .description(routeEntity.getDescription())
                        .build()).collect(Collectors.toSet());
    }

    @Named("toCreatedPoint")
    default Set<PointForProfile> toCreatedPoint(Set<PointOfInterestEntity> pointOfInterestEntities) {
        if (pointOfInterestEntities == null || pointOfInterestEntities.isEmpty()) {
            return null;
        }

        return pointOfInterestEntities.stream()
                .map(pointOfInterestEntity -> PointForProfile.builder()
                        .id(pointOfInterestEntity.getId())
                        .name(pointOfInterestEntity.getName())
                        .description(pointOfInterestEntity.getDescription())
                        .build()).collect(Collectors.toSet());

    }

    @Named("toEstimatedPoint")
    default Set<PointForProfile> toEstimatedPoint(Set<PointOfInterestEntity> pointOfInterestEntities) {
        if (pointOfInterestEntities == null || pointOfInterestEntities.isEmpty()) {
            return null;
        }

        return pointOfInterestEntities.stream()
                .map(pointOfInterestEntity -> PointForProfile.builder()
                        .id(pointOfInterestEntity.getId())
                        .name(pointOfInterestEntity.getName())
                        .description(pointOfInterestEntity.getDescription())
                        .build()).collect(Collectors.toSet());
    }

    @Named("toEmail")
    default String toEmail(CredentialEntity credentialEntity) {
        if (credentialEntity == null) {
            return null;
        }
        return credentialEntity.getEmail();
    }


}
