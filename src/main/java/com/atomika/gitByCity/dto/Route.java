package com.atomika.gitByCity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Route {
    private Long id;
    private String name;
    private String description;
    private Long clientId;
    private List<Long> likes;
    private List<Long> pointOfInterestRoutesDto;
    private List<Comment> comments;
    //private List<Route> variationsDto;
    //private Route routeDto;
}
