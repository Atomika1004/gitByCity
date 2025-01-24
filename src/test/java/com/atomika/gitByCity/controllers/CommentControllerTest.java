package com.atomika.gitByCity.controllers;

import com.atomika.gitByCity.dto.Comment;
import com.atomika.gitByCity.dto.Role;
import com.atomika.gitByCity.dto.Route;
import com.atomika.gitByCity.dto.auth.SignInRequest;
import com.atomika.gitByCity.entity.*;
import com.atomika.gitByCity.repositories.CommentRepository;
import com.atomika.gitByCity.repositories.CredentialRepository;
import com.atomika.gitByCity.repositories.PointOfInterestRepository;
import com.atomika.gitByCity.repositories.RouteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestcontainersConfig.class)
@AutoConfigureMockMvc

class CommentControllerTest {

    private static final Logger log = LogManager.getLogger(CommentControllerTest.class);
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private CommentController commentController;


    @BeforeAll
    @WithMockUser(username = "trueUsername")
    static void setup(@Autowired CredentialRepository credentialRepository,
                      @Autowired PointOfInterestRepository pointOfInterestRepository,
                      @Autowired RouteRepository routeRepository,
                      @Autowired RouteController routeController) {
            SignInRequest signInRequest = SignInRequest.builder()
                    .fio("True fio")
                    .email("trueEmail@gmail.com")
                    .username("trueUsername")
                    .pwd("truePassword")
                    .build();


            CredentialEntity client = CredentialEntity.builder()
                    .username(signInRequest.getUsername())
                    .email(signInRequest.getEmail())
                    .password(new PasswordEntity(signInRequest.getPwd()))
                    .enabled(true)
                    .role(Role.CLIENT)
                    .build();

            ClientEntity client1 = ClientEntity.builder().
                    fio(signInRequest.getFio()).
                    credential(client).
                    build();


            client.setClient(client1);
            credentialRepository.save(client);

            String message = "Вы успешно зарегистрировались";


            pointOfInterestRepository.deleteAll();

            List<PointOfInterestEntity> list = List.of(
                    PointOfInterestEntity.builder()
                            .clientId(1L)
                            .name("first point")
                            .description("description first point")
                            .longitude(123)
                            .latitude(333)
                            .build(),
                    PointOfInterestEntity.builder()
                            .clientId(1L)
                            .name("second point")
                            .description("description second point")
                            .longitude(123)
                            .latitude(333)
                            .build()
            );
            pointOfInterestRepository.saveAll(list);

            Route route = Route.builder()
                            .name("first route")
                            .description("description first route")
                            .clientId(1L)
                            .pointOfInterestRoutesIds(List.of(1L, 2L))
                            .build();

            routeController.addRoute(route);
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void getCommentsForRoute() throws Exception {
        var resGetCommForRoute = mockMvc.perform(get("/api/base/comments/route/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String responseContent = resGetCommForRoute.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void getCommentsForPoint() throws Exception {
        var resGetCommForPoint = mockMvc.perform(get("/api/base/comments/point/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String responseContent = resGetCommForPoint.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void addCommentForPoint() throws Exception {
        Comment comment = Comment.builder()
                .text("First comment for point")
                .clientId(1L)
                .pointOfInterestId(1L)
                .routeId(null)
                .build();

        var resAddCommForPoint = mockMvc.perform(post("/api/base/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("First comment for point"))
                .andReturn();

        String responseContent = resAddCommForPoint.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void addCommentForRoute() throws Exception {

        Comment comment = Comment.builder()
                .text("First comment for route")
                .clientId(1L)
                .pointOfInterestId(null)
                .routeId(1L)
                .build();

        var resAddCommForPoint = mockMvc.perform(post("/api/base/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("First comment for route"))
                .andReturn();

        String responseContent = resAddCommForPoint.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void updateCommentForPoint() throws Exception {

        Comment comment = Comment.builder()
                .text("First comment for point")
                .clientId(1L)
                .pointOfInterestId(1L)
                .routeId(null)
                .build();

        mockMvc.perform(post("/api/base/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("First comment for point"))
                .andReturn();

        Comment commentForUpdate = Comment.builder()
                .id(1L)
                .text("Update comment for point 1")
                .clientId(1L)
                .pointOfInterestId(1L)
                .routeId(null)
                .build();

        log.info(commentController.getCommentsForPoint(1L));

        var resAddCommForPoint = mockMvc.perform(put("/api/base/comments/" + comment.getPointOfInterestId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentForUpdate)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("Update comment for point 1"))
                .andReturn();

        String responseContent = resAddCommForPoint.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);

    }

    @Test
    @WithMockUser(username = "trueUsername")
    void updateCommentForRoute() throws Exception {

        Comment comment = Comment.builder()
                .text("First comment for point")
                .clientId(1L)
                .pointOfInterestId(null)
                .routeId(1L)
                .build();

        mockMvc.perform(post("/api/base/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("First comment for point"))
                .andReturn();

        Comment commentForUpdate = Comment.builder()
                .id(1L)
                .text("Update comment for route 1")
                .clientId(1L)
                .pointOfInterestId(null)
                .routeId(1L)
                .build();


        var resAddCommForRoute = mockMvc.perform(put("/api/base/comments/" + comment.getPointOfInterestId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentForUpdate)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("Update comment for route 1"))
                .andReturn();

        String responseContent = resAddCommForRoute.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void deleteCommentForPoint() throws Exception {
        long idCommForDeleteFromPoint = 6L;

        Comment comment = Comment.builder()
                .text("First comment for point")
                .clientId(1L)
                .pointOfInterestId(1L)
                .routeId(null)
                .build();

        mockMvc.perform(post("/api/base/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("First comment for point"))
                .andReturn();


        var response = mockMvc.perform(delete("/api/base/comments/" + idCommForDeleteFromPoint))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("6"))
                .andReturn();

        String responseContent = response.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);

    }

    @Test
    @WithMockUser(username = "trueUsername")
    void deleteCommentFromRoute() throws Exception {

        long idCommForDeleteFromRoute = 1L;

        Comment comment = Comment.builder()
                .text("First comment for route")
                .clientId(1L)
                .pointOfInterestId(null)
                .routeId(1L)
                .build();

        mockMvc.perform(post("/api/base/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("First comment for route"))
                .andReturn();


        var response = mockMvc.perform(delete("/api/base/comments/" + idCommForDeleteFromRoute))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("1"))
                .andReturn();

        String responseContent = response.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);
    }
}