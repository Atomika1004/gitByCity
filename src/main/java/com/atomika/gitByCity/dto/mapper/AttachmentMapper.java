package com.atomika.gitByCity.dto.mapper;

import com.atomika.gitByCity.dto.Attachment;
import com.atomika.gitByCity.entity.AttachmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

//    AttachmentMapper INSTANCE = Mappers.getMapper(AttachmentMapper.class);

    AttachmentEntity dtoToEntity (Attachment attachment);

    Attachment entityToDto (AttachmentEntity attachmentEntity);

    List<Attachment> toList(List<AttachmentEntity> attachmentEntityList);

    List<AttachmentEntity> toListEntity(List<Attachment> imagesDto);
}

