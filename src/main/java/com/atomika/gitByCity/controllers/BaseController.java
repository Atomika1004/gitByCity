package com.atomika.gitByCity.controllers;

import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.dto.Credential;
import com.atomika.gitByCity.dto.PointOfInterest;
import com.atomika.gitByCity.dto.Route;
import com.atomika.gitByCity.dto.mapper.ClientMapper;
import com.atomika.gitByCity.dto.mapper.PointOfInterestMapper;
import com.atomika.gitByCity.dto.mapper.RouteMapper;
import com.atomika.gitByCity.entity.ClientEntity;
import com.atomika.gitByCity.repositories.ClientRepository;
import com.atomika.gitByCity.repositories.PointOfInterestRepository;
import com.atomika.gitByCity.repositories.RouteRepository;
import com.atomika.gitByCity.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/base/client")
public class BaseController {

    private final ClientService clientService;

    @GetMapping("check")
    public String sayHello() {
        return "Hello" + new Date();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("profile")
    public Client getClient() {
        return clientService.getClientForProfile();
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("isLike")
    public Long getClientId() {
        return clientService.getClientId();
    }

}
