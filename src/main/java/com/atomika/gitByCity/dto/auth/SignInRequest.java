package com.atomika.gitByCity.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInRequest {  // отправление запросов на регистрацию пользователей
    private String username;
    private String fio;
    private String pwd;
}
