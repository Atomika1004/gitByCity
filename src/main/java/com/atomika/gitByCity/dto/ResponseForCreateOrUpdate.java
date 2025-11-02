package com.atomika.gitByCity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseForCreateOrUpdate {
    private String message;
    private boolean success;
}