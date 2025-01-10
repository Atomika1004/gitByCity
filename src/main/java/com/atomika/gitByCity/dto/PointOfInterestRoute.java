package com.atomika.gitByCity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PointOfInterestRoute {
    private Long id;
    private Long pointOfInterestId;
    private Long routeId;
    private int position;
}
