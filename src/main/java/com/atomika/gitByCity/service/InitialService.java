package com.atomika.gitByCity.service;

import com.atomika.gitByCity.service.auth.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialService {

    private final CustomUserDetailsService userDetailsService;


    public void initial() {

//        userDetailsService.createClient(SignInRequest.builder()
//                .username("user1")
//                .pwd("1234")
//                .build());
//
//        userDetailsService.createClient(SignInRequest.builder()
//                .username("user2")
//                .pwd("12345")
//                .build());


//        userDetailsService.createAdmin(SignInRequest.builder()
//                .username("admin1")
//                .pwd("12345")
//                .build());


    }
}
