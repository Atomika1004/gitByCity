package com.atomika.gitByCity.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponse {
    String message;
}
