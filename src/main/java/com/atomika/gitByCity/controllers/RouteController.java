package com.atomika.gitByCity.controllers;

import com.atomika.gitByCity.dto.Route;
import com.atomika.gitByCity.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/base/routes")
public class RouteController {

    private final RouteService routeService;

    @GetMapping()
    public List<Route> getAllRoutes() {
        return routeService.findAll();
    }

    @GetMapping("{id}")
    public Route getRouteById(@PathVariable Long id) {
        return routeService.findById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Route addRoute(@RequestBody Route route) {
        String clientName = AuthorizationController.getCurrentUsername();
        return routeService.create(route, clientName);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@routeService.isCreator(authentication.name, #id)")
    public Long deleteRoute(@PathVariable("id") long id) {
        return routeService.delete(id);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("@routeService.isCreator(authentication.name, #route.id)")
    public Route updateRoute(@RequestBody Route route) {
         return routeService.update(route);
    }

    @PostMapping(value = "/{routeId}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long addLike(@PathVariable Long routeId, @RequestParam Long clientId) {
        return routeService.addLike(routeId, clientId);
    }

    @DeleteMapping(value = "/{routeId}/dislike", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteLike(@PathVariable Long routeId, @RequestParam Long clientId) {
        return routeService.deleteLike(routeId, clientId);
    }
}
