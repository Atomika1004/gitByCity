package com.atomika.gitByCity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Credential {
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private Role role;
}
