package com.atomika.gitByCity.controllers;

import com.atomika.gitByCity.dto.Client;
import com.atomika.gitByCity.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
