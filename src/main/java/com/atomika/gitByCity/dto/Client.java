package com.atomika.gitByCity.dto;

import com.atomika.gitByCity.entity.PointOfInterestEntity;
import com.atomika.gitByCity.entity.RouteEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Client {
    private Long id;
    private String fio;
    private String email;
    private List<Route> createdRoute;
    private List<Route> estimatedRoute;
    private Credential credential;
    private List<PointOfInterest> estimatedPointOfInterest;
    private List<PointOfInterest> createdPointOfInterest;
}
