package com.atomika.gitByCity.service;

import com.atomika.gitByCity.dto.Admin;
import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.dto.Credential;
import com.atomika.gitByCity.dto.Role;
import com.atomika.gitByCity.dto.auth.SignInRequest;
import com.atomika.gitByCity.entity.CredentialEntity;
import com.atomika.gitByCity.service.auth.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialService {
    private final CredentialService credentialService;
    private final AdminService adminService;
    private final ClientService clientService;

    private final JwtUserDetailsService userDetailsService;


    public void initial() {

        userDetailsService.createClient(SignInRequest.builder()
                .username("user1")
                .pwd("1234")
                .build());

        userDetailsService.createClient(SignInRequest.builder()
                .username("user2")
                .pwd("12345")
                .build());


//        userDetailsService.createAdmin(SignInRequest.builder()
//                .username("admin1")
//                .pwd("12345")
//                .build());


    }
}
