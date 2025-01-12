package com.atomika.gitByCity.controllers;

import com.atomika.gitByCity.dto.PointOfInterest;
import com.atomika.gitByCity.service.PointOfInterestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/base/points")
public class PointOfInterestController {

    private final PointOfInterestService pointOfInterestService;

    @GetMapping()
    public List<PointOfInterest> getAllPointsOfInterest(){
        return pointOfInterestService.findAll();
    }

    @GetMapping("{id}")
    public PointOfInterest getPointOfInterestById(@PathVariable Long id){
        return pointOfInterestService.findById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PointOfInterest createPointOfInterest(@RequestBody PointOfInterest pointOfInterest) {
        String clientName = AuthorizationController.getCurrentUsername();
        return pointOfInterestService.create(pointOfInterest, clientName);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@pointOfInterestService.isCreator(authentication.name, #id)")
    public Long deletePointOfInterest(@PathVariable("id") long id) {
       return pointOfInterestService.delete(id);
    }

    @PutMapping(value = "update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@pointOfInterestService.isCreator(authentication.name, #pointOfInterest.id)")
    public PointOfInterest updatePointOfInterest(@RequestBody PointOfInterest pointOfInterest) {
        return pointOfInterestService.update(pointOfInterest);
    }

    @PostMapping(value = "/{pointId}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long addLike(@PathVariable Long pointId, @RequestParam String clientUsername) {
        return pointOfInterestService.addLike(pointId, clientUsername);
    }


}
