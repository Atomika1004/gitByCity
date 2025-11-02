package com.atomika.gitByCity.rest;

import com.atomika.gitByCity.dto.auth.*;
import com.atomika.gitByCity.service.AuthorizationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorizationController {
    AuthorizationService authorizationService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createToken(@RequestBody JwtRequest request) throws Exception {
        JwtResponse response = authorizationService.authenticateUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/registration")
    public ResponseEntity<SignInResponse> registerUser(@RequestBody SignInRequest request) {
        SignInResponse response = authorizationService.registration(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshDto request) {
        JwtResponse response = authorizationService.refreshToken(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}