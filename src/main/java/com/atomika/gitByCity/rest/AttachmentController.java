package com.atomika.gitByCity.rest;

import com.atomika.gitByCity.dto.Attachment;
import com.atomika.gitByCity.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/upload")
    public String uploadImage(@ModelAttribute final Attachment imageDto) {
        log.info("Uploading image {}", imageDto);
        return attachmentService.upload(imageDto);
    }
}