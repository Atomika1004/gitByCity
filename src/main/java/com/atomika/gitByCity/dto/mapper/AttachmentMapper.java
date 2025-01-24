package com.atomika.gitByCity.dto.mapper;

import com.atomika.gitByCity.dto.Attachment;
import com.atomika.gitByCity.entity.AttachmentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    AttachmentEntity dtoToEntity (Attachment attachment);

    Attachment entityToDto (AttachmentEntity attachmentEntity);

    List<Attachment> toList(List<AttachmentEntity> attachmentEntityList);

}

