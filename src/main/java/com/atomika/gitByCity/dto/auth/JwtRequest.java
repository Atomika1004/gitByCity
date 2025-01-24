package com.atomika.gitByCity.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtRequest { // оптравление запроса на получение токена
    private String username;
    private String password;
}
