package com.atomika.gitByCity.service;

import com.atomika.gitByCity.configuration.MinioConfig;
import com.atomika.gitByCity.dto.Attachment;
import com.atomika.gitByCity.dto.mapper.AttachmentMapper;
import com.atomika.gitByCity.handler.exception.UploadImageException;
import com.atomika.gitByCity.repositories.AttachmentRepository;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    private final AttachmentMapper attachmentMapper;

    private final MinioClient minioClient;

    private final MinioConfig minioConfig;

    public String upload(Attachment attachment) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new UploadImageException("Не удалось загрузить изображение " + e.getMessage());
        }

        MultipartFile file = attachment.getImage();
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new UploadImageException("Имя для фотографии не должно быть пустым");
        }
        String fileName = generateFileName(file);
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new UploadImageException("Не удалось загрузить изображение " + e.getMessage());
        }
        saveImage(inputStream, fileName);
        return fileName;
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioConfig.getBucket())
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .build());
        }
    }

    private String generateFileName(MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }

    private String getExtension(MultipartFile file) {
        return file.getOriginalFilename()
                .substring(file.getOriginalFilename()
                        .lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void saveImage(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioConfig.getBucket())
                .object(fileName)
                .build());
    }

    public Attachment save(Attachment attachment) {
        String name = upload(attachment);
        return attachmentMapper.entityToDto(attachmentRepository.save(attachmentMapper.dtoToEntity(attachment)));
    }
}
