package com.atomika.gitByCity.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class Attachment {
    private Long id;

    private Long pointOfInterestId;

    private MultipartFile image;

}
