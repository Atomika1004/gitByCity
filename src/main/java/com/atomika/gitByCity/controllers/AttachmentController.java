package com.atomika.gitByCity.controllers;

import com.atomika.gitByCity.dto.Attachment;
import com.atomika.gitByCity.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/base/image")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping()
    public String uploadImage(@ModelAttribute final Attachment imageDto) {
        log.info("Uploading image {}", imageDto);
        return attachmentService.upload(imageDto);
    }
}
