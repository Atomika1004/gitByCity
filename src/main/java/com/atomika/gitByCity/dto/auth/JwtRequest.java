package com.atomika.gitByCity.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtRequest {
    private String username;
    private String password;
}
