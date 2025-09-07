package com.atomika.gitByCity.repositories;

import com.atomika.gitByCity.dto.Attachment;
import com.atomika.gitByCity.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {
}
