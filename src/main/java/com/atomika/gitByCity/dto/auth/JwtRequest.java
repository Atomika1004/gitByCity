package com.atomika.gitByCity.dto.auth;

import lombok.Data;

@Data
public class JwtRequest { // оптравление запроса на получение токена
    private String username;
    private String password;
}
