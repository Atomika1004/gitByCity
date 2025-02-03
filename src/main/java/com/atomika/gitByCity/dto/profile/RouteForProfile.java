package com.atomika.gitByCity.dto.profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteForProfile {
    private long id;
    private String name;
    private String description;
}
