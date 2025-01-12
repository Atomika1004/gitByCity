package com.atomika.gitByCity.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInRequest {  // отправление запросов на регистрацию пользователей
    private String fio;
    private String username;
    private String pwd;
}
