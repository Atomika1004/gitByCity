package com.atomika.gitByCity.dto.profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PointForProfile {
    private long id;
    private String name;
    private String description;
}
