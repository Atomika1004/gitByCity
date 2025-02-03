package com.atomika.gitByCity.dto;

import com.atomika.gitByCity.dto.profile.PointForProfile;
import com.atomika.gitByCity.dto.profile.RouteForProfile;
import com.atomika.gitByCity.entity.PointOfInterestEntity;
import com.atomika.gitByCity.entity.RouteEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class Client {
    private Long id;
    private String fio;
    private String email;
    private Set<RouteForProfile> createdRoute;
    private Set<RouteForProfile> estimatedRoute;
    private Set<PointForProfile> estimatedPointOfInterest;
    private Set<PointForProfile> createdPointOfInterest;
}
