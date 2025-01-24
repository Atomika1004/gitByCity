package com.atomika.gitByCity.dto.mapper;

import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.dto.PointOfInterest;
import com.atomika.gitByCity.entity.AttachmentEntity;
import com.atomika.gitByCity.entity.PointOfInterestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, AttachmentMapper.class})
public interface PointOfInterestMapper {


    @Mapping(target = "likes" , ignore = true)
    @Mapping(target = "images", source = "images", qualifiedByName = "toAttachmentEntities")
    PointOfInterestEntity dtoToEntity(PointOfInterest pointOfInterest);

    @Mapping(target = "likes", source = "likes", qualifiedByName = "toLike")
    @Mapping(target = "images", source = "images", qualifiedByName = "toImages")
    PointOfInterest entityToDto(PointOfInterestEntity pointOfInterestEntity);

    List<PointOfInterest> toList(List<PointOfInterestEntity> pointOfInterestEntityList);

    @Named("toLike")
    default List<Long> toLike(List<Client> clientList) {
        if (clientList == null || clientList.isEmpty()) {
            return null;
        }
        return clientList.stream().map(Client::getId).toList();
    }

    @Named("toImages")
    default List<String> toImages(List<AttachmentEntity> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }
        return images.stream()
                .map(AttachmentEntity::getImageUrl)
                .toList();
    }

    @Named("toAttachmentEntities")
    default List<AttachmentEntity> toAttachmentEntities(List<String> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }
        return images.stream().map(image -> {
            AttachmentEntity attachmentEntity = new AttachmentEntity();
            attachmentEntity.setImageUrl(image);
            attachmentEntity.setRouteId(null);
            return attachmentEntity;
        }).collect(Collectors.toList());
    }
}
