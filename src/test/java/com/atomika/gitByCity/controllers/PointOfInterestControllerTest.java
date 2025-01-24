package com.atomika.gitByCity.controllers;

import com.atomika.gitByCity.dto.PointOfInterest;
import com.atomika.gitByCity.dto.Role;
import com.atomika.gitByCity.dto.auth.SignInRequest;
import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.entity.CredentialEntity;
import com.atomika.gitByCity.entity.PasswordEntity;
import com.atomika.gitByCity.entity.PointOfInterestEntity;
import com.atomika.gitByCity.repositories.CredentialRepository;
import com.atomika.gitByCity.repositories.PointOfInterestRepository;
import com.atomika.gitByCity.service.PointOfInterestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TestcontainersConfig.class)
//@Testcontainers
@AutoConfigureMockMvc
//@Import(TestcontainersConfig.class)
class PointOfInterestControllerTest{

    private static final Logger log = LoggerFactory.getLogger(PointOfInterestControllerTest.class);
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PointOfInterestRepository pointOfInterestRepository;

    @Autowired
    PointOfInterestController pointOfInterestController;

    @Autowired
    PointOfInterestService pointOfInterestService;

    @BeforeAll
    static void setup(@Autowired CredentialRepository credentialRepository,
                      @Autowired PointOfInterestRepository pointOfInterestRepository)  {
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
        log.info("Credential created{}", credentialRepository.findAll().size());
        String message = "Вы успешно зарегистрировались";
    }

    @BeforeEach
    void setUp() throws Exception {
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
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void addNewPoint_isValid() throws Exception {
        PointOfInterest pointOfInterest = PointOfInterest.builder()
//                .clientId(1L)
                .name("first2343324 pointaaaaa")
                .description("test description first")
                .longitude(37.620301)
                .latitude(55.754093)
                .build();

//        ResponseForCreateOrUpdate rs = pointOfInterestController.createPointOfInterest(pointOfInterest).getBody();
//        log.info(rs.getMessage());
//        when(pointOfInterestService.create(pointOfInterest, "trueUsername" ))
//                .thenReturn(responseForCreateOrUpdate);


        var resultAddPoint = mockMvc.perform(post("/api/base/points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(pointOfInterest)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Точка успешно создана, поздравляю!!!"))
                .andReturn();

        String responseContent = resultAddPoint.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);
        //log.info(resultAddPoint.getResponse().getContentAsString());
        assertThat(pointOfInterestRepository.findAll()).hasSize(3);
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void addNewPoint_NoValid() throws Exception {
        PointOfInterest pointOfInterest = PointOfInterest.builder()
                .clientId(1L)
                .name("second point")
                .description("test description2")
                .longitude(37.620301)
                .latitude(55.754093)
                .build();

//        when(pointOfInterestController.createPointOfInterest(any(PointOfInterest.class)))
//                .thenReturn(new ResponseEntity<>(responseForCreateOrUpdate, HttpStatus.BAD_REQUEST));


        var resultAddPoint = mockMvc.perform(post("http://localhost:8081/api/base/points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(pointOfInterest)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Точка с таким названием уже существует"))
                .andReturn();

        String responseContent = resultAddPoint.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void getAllPointsOfInterest_isValid() throws Exception {

        List<PointOfInterest> pointsOfInterest = pointOfInterestService.findAll();

//        when(pointOfInterestController.getAllPointsOfInterest())
//                .thenReturn(new ResponseEntity<>(pointsOfInterest, HttpStatus.OK));

        var result = mockMvc.perform(get("/api/base/points")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(pointsOfInterest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("first point"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("second point"))
                .andReturn();


        String responseContent = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);;
    }


    @Test
    @WithMockUser(username = "trueUsername")
    void getOnePointsOfInterestById_Valid() throws Exception {
            long pointOfInterestId = 1L;

//            PointOfInterest pointOfInterest = pointOfInterestService.findById(pointOfInterestId);
//            when(pointOfInterestController.getPointOfInterestById(pointOfInterestId))
//                    .thenReturn(new ResponseEntity<>(pointOfInterest, HttpStatus.OK));

            var res = mockMvc.perform(get("/api/base/points/" + pointOfInterestId)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("first point"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").value(1L))
                    .andReturn();


        String responseContent = res.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);

    }

    @Test
    @WithMockUser(username = "trueUsername")
    void getOnePointsOfInterestById_noValid() throws Exception {
        Long pointOfInterestId = 3L;

//        when(pointOfInterestController.getPointOfInterestById(pointOfInterestId))
//                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/base/points/" + pointOfInterestId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        assertNull(pointOfInterestService.findById(pointOfInterestId));
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void updatePointOfInterest_Valid() throws Exception {
        //given
        PointOfInterest pointOfInterest = PointOfInterest.builder()
                .id(4L)
                .name("update Name")
                .description("update Description")
                .clientId(1L)
                .longitude(111)
                .latitude(222)
                .build();
        //when
//        ResponseForCreateOrUpdate response = ResponseForCreateOrUpdate.builder()
//                .message("Success")
//                .success(true)
//                .build();
//        when(pointOfInterestController.updatePointOfInterest(pointOfInterest))
//                .thenReturn(response);
//        when(pointOfInterestService.update(pointOfInterest)).thenReturn(response);
//        ResponseForCreateOrUpdate responseForUpdate = pointOfInterestController.updatePointOfInterest(pointOfInterest);
        //then

        log.info(pointOfInterestController.getAllPointsOfInterest().toString());

        var result = mockMvc.perform(put("/api/base/points/update/" + "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(pointOfInterest)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Точка успешно сохранена, поздравляю!!!"))
                .andReturn();


        String responseContent = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);;
        log.info(pointOfInterestService.findById(pointOfInterest.getId()).toString());
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void updatePointOfInterest_NoValid() throws Exception {
        //given
        PointOfInterest pointOfInterest = PointOfInterest.builder()
                .id(6L)
                .name("second point")
                .description("update Description")
                .clientId(1L)
                .longitude(111)
                .latitude(222)
                .build();


        //when
//        ResponseForCreateOrUpdate response = ResponseForCreateOrUpdate.builder()
//                .message("Success")
//                .success(true)
//                .build();
//        when(pointOfInterestController.updatePointOfInterest(pointOfInterest))
//                .thenReturn(response);
//        when(pointOfInterestService.update(pointOfInterest)).thenReturn(response);
//        ResponseForCreateOrUpdate responseForUpdate = pointOfInterestController.updatePointOfInterest(pointOfInterest);
        //then

        var result = mockMvc.perform(put("/api/base/points/update/" + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(pointOfInterest)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Точка с таким названием уже существует"))
                .andReturn();


        String responseContent = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);;
        log.info(pointOfInterestService.findById(pointOfInterest.getId()).toString());
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void deletePointOfInterest_Valid() throws Exception {

        //when
        var result = mockMvc.perform(delete("/api/base/points/" + "8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("8"))
                .andReturn();
        //then
        String responseContent = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);;

    }

    @Test
    @WithMockUser(username = "trueUsername")
    void deletePointOfInterest_noValid() throws Exception {
        //when
        var result = mockMvc.perform(delete("/api/base/points/" + "4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
//                .andExpect(content().string("4"))
                .andReturn();
        //then
        String responseContent = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);;
    }

    @Test
    @WithMockUser(username = "trueUsername")
    void addLikePointOfInterest_Valid() throws Exception {
//        PointOfInterest pointOfInterest = PointOfInterest.builder()
//                .name("add like to point")
//                .description("Description")
//                .clientId(1L)
//                .longitude(111999)
//                .latitude(222999)
//                .build();
//
//        pointOfInterestController.createPointOfInterest(pointOfInterest);

        log.info(pointOfInterestController.getAllPointsOfInterest().toString());
        var resultAddLike = mockMvc.perform(post("/api/base/points/11/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clientUsername", "trueUsername"))
//                        .content(new ObjectMapper().writeValueAsString("trueUsername")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("1"))
                .andReturn();

        String responseContent = resultAddLike.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);;
    }


    @Test
    @WithMockUser(username = "trueUsername")
    void deleteLikePointOfInterest_Valid() throws Exception {
//        PointOfInterest pointOfInterest = PointOfInterest.builder()
//                .name("delete like from point")
//                .description("update Description")
//                .clientId(1L)
//                .longitude(111)
//                .latitude(222)
//                .build();
//
//        pointOfInterestController.createPointOfInterest(pointOfInterest);
//
//        mockMvc.perform(post("/api/base/points/3/like")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("clientUsername", "trueUsername"))
//                .andExpect(status().is2xxSuccessful());

        var resultAddLike = mockMvc.perform(post("/api/base/points/3/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clientUsername", "trueUsername"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(""))
                .andReturn();

        String responseContent = resultAddLike.getResponse().getContentAsString(StandardCharsets.UTF_8);
        log.info(responseContent);;
    }

}
