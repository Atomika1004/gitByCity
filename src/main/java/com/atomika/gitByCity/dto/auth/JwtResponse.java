package com.atomika.gitByCity.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse { // получение ответа в виде токена
    private final String token;
}
