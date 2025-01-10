package com.atomika.gitByCity.dto;

import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.List;

@Data
@Builder
public class PointOfInterest {
    private Long id;
    private Long clientId;
    private String name;
    private String description;
    private List<Long> likes;
    private List<String> images;
    private double longitude;
    private double latitude;
}
