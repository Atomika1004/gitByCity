//package com.atomika.gitByCity.controllers;
//
//import com.atomika.gitByCity.dto.auth.JwtRequest;
//import com.atomika.gitByCity.dto.auth.JwtResponse;
//import com.atomika.gitByCity.dto.auth.SignInRequest;
//import com.atomika.gitByCity.dto.auth.SignInResponse;
//import com.atomika.gitByCity.repositories.ClientRepository;
//import com.atomika.gitByCity.service.auth.JwtUserDetailsService;
//import com.atomika.gitByCity.service.auth.TokenManager;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//
//@WebMvcTest(AuthorizationController.class)
//class AuthorizationControllerTest {
//
//    private static final Logger log = LoggerFactory.getLogger(AuthorizationControllerTest.class);
//    @MockBean
//    private JwtUserDetailsService userDetailsService;
//
//    @MockBean
//    private AuthenticationManager authenticationManager;
//
//    @MockBean
//    private TokenManager tokenManager;
//
//    @MockBean
//    private ClientRepository clientRepository;
//
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @WithMockUser(username = "user1")
//    void testValidAuth() throws Exception {
//        JwtRequest jwtRequest = JwtRequest.builder()
//                .username("user1")
//                .password("1234")
//                .build();
//
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenReturn(new UsernamePasswordAuthenticationToken(
//                        jwtRequest.getUsername(),
//                        jwtRequest.getPassword())
//                );
//
//        UserDetails userDetails = mock(UserDetails.class);
//        when(userDetailsService.loadUserByUsername(jwtRequest.getUsername()))
//                .thenReturn(userDetails);
//
//        String jwtToken = "jwtToken";
//        when(tokenManager.generateJwtToken(userDetails))
//                .thenReturn(jwtToken);
//
//        JwtResponse expectedResponse = new JwtResponse(jwtToken);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(jwtRequest))
//                        .with(csrf()))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(jwtToken));
//
//
//        log.info("JWT Token: {}", expectedResponse);
//    }
//
//    @Test
//    @WithMockUser(username = "user1")
//    void testValidRegistration() throws Exception {
//        SignInRequest signInRequest = SignInRequest.builder()
//                .fio("True fio")
//                .email("trueEmail@gmail.com")
//                .username("trueUsername")
//                .pwd("truePassword")
//                .build();
//
//        String message = "Вы успешно зарегистрировались";
//
//        when(userDetailsService.createClient(signInRequest))
//                .thenReturn(SignInResponse.builder()
//                        .message(message)
//                        .build()
//                );
//
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/registration")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(signInRequest))
//                        .with(csrf()))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message));
//
//        log.info("Message: {}", message);
//    }
//
//}