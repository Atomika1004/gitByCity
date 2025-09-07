package com.atomika.gitByCity.dto.mapper;

import com.atomika.gitByCity.dto.Attachment;
import com.atomika.gitByCity.entity.AttachmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    @Mapping(target = "imageUrl", source = "image", qualifiedByName = "toImageName")
    AttachmentEntity dtoToEntity (Attachment attachment);

//    @Mapping(target = "image", source = "imageUrl", qualifiedByName = "toMultipartFile")
    Attachment entityToDto (AttachmentEntity attachmentEntity);

    List<Attachment> toList(List<AttachmentEntity> attachmentEntityList);

    @Named("toImageName")
    default String toImageName(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            return null;
        }
        return file.getOriginalFilename();
    }

}

