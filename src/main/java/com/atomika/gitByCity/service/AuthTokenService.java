package com.atomika.gitByCity.service;

import com.atomika.gitByCity.entity.AuthTokenEntity;
import com.atomika.gitByCity.handler.exception.NotFoundException;
import com.atomika.gitByCity.repositories.AuthTokenRepository;
import com.atomika.gitByCity.service.auth.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class AuthTokenService {
    final AuthTokenRepository authTokenRepository;
    final JwtService jwtService;

    @Value("${token.expired.refresh}")
    Long tokenExpiredRefresh;

    public AuthTokenEntity getAuthToken(String token) {
        return authTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Запись с информацией о токене не найдена"));
    }

    public void createOrUpdateAuthToken(String accessToken, String refreshToken) {
        Long currenCredentialId = jwtService.getCredentialId(accessToken);
        authTokenRepository.findByCredentialId(currenCredentialId)
                .map(authToken -> update(authToken, accessToken, refreshToken))
                .orElseGet(() -> create(accessToken, refreshToken));
    }

    @NotNull
    private AuthTokenEntity create(String accessToken, String refreshToken) {
        AuthTokenEntity authToken = new AuthTokenEntity();
        authToken.setAccessToken(accessToken);
        authToken.setRefreshToken(refreshToken);
        authToken.setSuccessExpired(jwtService.getExpiredAccessToken(accessToken));
        authToken.setRefreshExpired(System.currentTimeMillis() + tokenExpiredRefresh);
        authToken.setCredentialId(jwtService.getCredentialId(accessToken));
        return authTokenRepository.save(authToken);
    }

    public AuthTokenEntity update(AuthTokenEntity authToken, String  accessToken, String refreshToken) {
        authToken.setAccessToken(accessToken);
        authToken.setRefreshToken(refreshToken);
        authToken.setSuccessExpired(jwtService.getExpiredAccessToken(accessToken));
        authToken.setRefreshExpired(System.currentTimeMillis() + tokenExpiredRefresh);
        authToken.setCredentialId(jwtService.getCredentialId(accessToken));
        return authTokenRepository.save(authToken);
    }

}