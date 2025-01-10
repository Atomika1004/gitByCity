package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.Attachment;
import com.atomika.gitByCity.dto.ImageUtil;
import com.atomika.gitByCity.dto.mapper.AttachmentMapper;
import com.atomika.gitByCity.entity.AttachmentEntity;
import com.atomika.gitByCity.repositories.AttachmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    public Attachment create (Attachment attachment) {
        return attachmentMapper.entityToDto(
                attachmentRepository.save(attachmentMapper.dtoToEntity(attachment)));
    }

    public Attachment update (Attachment attachment) {
        return attachmentMapper.entityToDto(
                attachmentRepository.save(attachmentMapper.dtoToEntity(attachment)));
    }

    public Long delete (Long id) {
        attachmentRepository.deleteById(id);
        return id;
    }

    public List<Attachment> findAll() {
        return attachmentMapper.toList(attachmentRepository.findAll());
    }

//    @Transactional
//    public Attachment uploadImage(MultipartFile file, Long pointOfInterestId) throws IOException {
//        Attachment attachment = Attachment.builder()
//                .name(file.getOriginalFilename())
//                .pointOfInterestId(pointOfInterestId)
//                .type(file.getContentType())
//                .imageData(ImageUtil.compressImage(file.getBytes()))
//                .build();
//
//        AttachmentEntity savedEntity = attachmentRepository.save(attachmentMapper.dtoToEntity(attachment));
//        return attachmentMapper.entityToDto(savedEntity);
//    }


//    @Transactional
//    public Attachment getInfoByImageByName(String name) {
//        Attachment dbImage = attachmentMapper.entityToDto(attachmentRepository.findByName(name));
//
//        return Attachment.builder()
//                .name(dbImage.getName())
//                .type(dbImage.getType())
//                .imageData(ImageUtil.decompressImage(dbImage.getImageData())).build();
//
//    }
//
//    @Transactional
//    public byte[] getImage(String name) {
//        Attachment dbImage = attachmentMapper.entityToDto(attachmentRepository.findByName(name));
//        return ImageUtil.decompressImage(dbImage.getImageData());
//    }
}
