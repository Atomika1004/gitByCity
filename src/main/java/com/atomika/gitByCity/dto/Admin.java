package com.atomika.gitByCity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Admin {
    private Long id;
    private String fio;
    private Credential credential;
}