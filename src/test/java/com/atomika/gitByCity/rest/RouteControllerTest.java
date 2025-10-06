package com.atomika.gitByCity.rest;

import com.atomika.gitByCity.dto.Role;
import com.atomika.gitByCity.dto.Route;
import com.atomika.gitByCity.dto.auth.SignInRequest;
import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.entity.CredentialEntity;
import com.atomika.gitByCity.entity.PasswordEntity;
import com.atomika.gitByCity.entity.PointOfInterestEntity;
import com.atomika.gitByCity.repositories.CredentialRepository;
import com.atomika.gitByCity.repositories.PointOfInterestRepository;
import com.atomika.gitByCity.repositories.RouteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TestcontainersConfig.class)
@AutoConfigureMockMvc
class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RouteController routeController;

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
                        .build(),
                PointOfInterestEntity.builder()
                        .clientId(1L)
                        .name("three point")
                        .description("description first point")
                        .longitude(123)
                        .latitude(333)
                        .build(),
                PointOfInterestEntity.builder()
                        .clientId(1L)
                        .name("four point")
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
    void getAllRoutes() throws Exception {
        var response = mockMvc.perform(get("/api/base/routes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String responseContent = response.getResponse().getContentAsString(StandardCharsets.UTF_8);
//        log.info(responseContent);
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void getRouteById() throws Exception {
        Route route = Route.builder()
                .name("second route")
                .description("description second route")
                .clientId(1L)
                .pointOfInterestRoutesIds(List.of(3L, 4L))
                .build();

        routeController.addRoute(route);

        var response = mockMvc.perform(get("/api/base/routes/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("after updated first route"))
                .andReturn();
        String responseContent = response.getResponse().getContentAsString(StandardCharsets.UTF_8);
//        log.info(responseContent);

    }

    @Test
    @WithMockUser(username = "trueUsername")
    void addRoute() throws Exception {
        Route route = Route.builder()
                .name("third route")
                .description("description for third route")
                .clientId(1L)
                .pointOfInterestRoutesIds(List.of(1L, 4L))
                .build();

        var response = mockMvc.perform(post("/api/base/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(route)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Маршрут успешно создан, поздравляю!!!"))
                .andReturn();
        String responseContent = response.getResponse().getContentAsString(StandardCharsets.UTF_8);
//        log.info(responseContent);
        //log.info(routeController.getAllRoutes());
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void deleteRoute() throws Exception {
        long routeId = 2L;

        var response = mockMvc.perform(delete("/api/base/routes/" + routeId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string("2"))
                .andReturn();

        String responseContent = response.getResponse().getContentAsString(StandardCharsets.UTF_8);
//        log.info(responseContent);
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void updateRoute() throws Exception {
        Route route = Route.builder()
                .name("before updated first route")
                .description("description for third route")
                .clientId(1L)
                .pointOfInterestRoutesIds(List.of(1L, 2L))
                .build();
        routeController.addRoute(route);

        //log.info(routeController.getAllRoutes());
        Route routeUpdate = Route.builder()
                .id(3L)
                .name("after updated first route")
                .description("description for third route")
                .clientId(1L)
                .pointOfInterestRoutesIds(List.of(1L, 3L))
                .build();

        var response = mockMvc.perform(put("/api/base/routes/update/3")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(routeUpdate)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Маршрут успешно сохранен, поздравляю!!!"))
                .andReturn();

        String responseContent = response.getResponse().getContentAsString(StandardCharsets.UTF_8);
//        log.info(responseContent);
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void addLike() throws Exception {
        long routeId = 1L;
        Route route = Route.builder()
                .name("test route for like")
                .description("description for third route")
                .clientId(1L)
                .pointOfInterestRoutesIds(List.of(1L, 2L))
                .build();
        routeController.addRoute(route);

        var response = mockMvc.perform(post("/api/base/routes/2/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clientUsername", "trueUsername"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string("1"))
                .andReturn();

        String responseContent = response.getResponse().getContentAsString(StandardCharsets.UTF_8);
//        log.info(responseContent);
        //log.info(routeController.getAllRoutes());
    }
}