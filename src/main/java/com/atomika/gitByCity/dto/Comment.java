package com.atomika.gitByCity.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Builder
public class Comment {
    private Long id;
    private Long clientId;
    private Long routeId;
    private Long pointOfInterestId;
    private String text;
}