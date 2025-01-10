package com.atomika.gitByCity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Client {
    private Long id;
    private String fio;
    private List<Route> createdRouteDto;
    private List<Long> estimatedRouteDto;
    private Credential credential;
//    private Long credentialId;

}
