package com.atomika.gitByCity.controllers;

import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.dto.Credential;
import com.atomika.gitByCity.dto.PointOfInterest;
import com.atomika.gitByCity.dto.Route;
import com.atomika.gitByCity.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/base")
public class BaseController {
    private final CredentialService credentialService;
    private final AdminService adminService;
    private final ClientService clientService;
    private final PointOfInterestService pointOfInterestService;
    private final RouteService routeService;

    @GetMapping("check")
    public String sayHello() {
        return "Hello" + new Date();
    }

//    @GetMapping()
//    public List<Credential> getAllCredential(){
//        return credentialService.findAll();
//    }
//
//
//    @GetMapping("client")
//    public List<Client> getAllClient(){
//        return clientService.findAll();
//    }

//    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public Client createClient(@RequestBody Client client) {
//        return clientService.create(client);
//    }



//    @GetMapping("routes")
//    public List<Route> getAllRoute(){
//        return routeService.findAll();
//    }
//
//    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public Route createRoute(@RequestBody Route route) {
//        return routeService.create(route);
//    }
}
