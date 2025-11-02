package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.AuthUser;
import com.atomika.gitByCity.dto.auth.*;
import com.atomika.gitByCity.entity.AuthTokenEntity;
import com.atomika.gitByCity.service.auth.CustomUserDetailsService;
import com.atomika.gitByCity.service.auth.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorizationService {
    CustomUserDetailsService userDetailsService;
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    AuthTokenService authTokenService;

    public JwtResponse generateToken(JwtRequest request) throws Exception {
        try {
            return authenticateUser(request);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public JwtResponse authenticateUser(JwtRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        AuthUser authUser = userDetailsService.loadUserByUsername(request.getUsername());
        String accessToken = jwtService.generateJwtToken(authUser);
        String refreshToken = jwtService.generateRefreshToken();
        authTokenService.createOrUpdateAuthToken(accessToken, refreshToken);
        return buildJwtResponse(accessToken, refreshToken);
    }

    public JwtResponse buildJwtResponse(String accessToken, String refreshToken) {
        Long expiredAccessToken = jwtService.getExpiredAccessToken(accessToken);
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(expiredAccessToken)
                .build();
    }

    public SignInResponse registration(SignInRequest request) {
        return userDetailsService.createClient(request);
    }

    public JwtResponse refreshToken(RefreshDto refreshDto) {
        AuthTokenEntity authToken = getByRefreshToken(refreshDto.getToken());
        if (authToken != null) {
            AuthUser authUser = userDetailsService.loadUserByUsername(
                    jwtService.getUsernameFromToken(authToken.getAccessToken()));
            String accessToken = jwtService.generateJwtToken(authUser);
            String refreshToken = jwtService.generateRefreshToken();
            authTokenService.createOrUpdateAuthToken(accessToken, refreshToken);
            return buildJwtResponse(accessToken, refreshToken);
        }

        throw new BadCredentialsException("INVALID_CREDENTIALS");
    }

    private AuthTokenEntity getByRefreshToken(String refreshToken) {
        AuthTokenEntity authToken = authTokenService.getAuthToken(refreshToken);
        return authToken;
    }

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }
}