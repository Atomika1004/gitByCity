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
}
