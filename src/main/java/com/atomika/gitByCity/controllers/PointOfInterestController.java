package com.atomika.gitByCity.controllers;

import com.atomika.gitByCity.dto.PointOfInterest;
import com.atomika.gitByCity.dto.ResponseForCreateOrUpdate;
import com.atomika.gitByCity.service.PointOfInterestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<PointOfInterest>> getAllPointsOfInterest(){
        return new ResponseEntity<>(pointOfInterestService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PointOfInterest> getPointOfInterestById(@PathVariable Long id) {
        PointOfInterest pointOfInterest = pointOfInterestService.findById(id);
        if (pointOfInterest == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pointOfInterest, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ResponseForCreateOrUpdate> createPointOfInterest(@RequestBody PointOfInterest pointOfInterest) {
        String clientName = AuthorizationController.getCurrentUsername();
        return new ResponseEntity<>(pointOfInterestService.create(pointOfInterest, clientName), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@pointOfInterestService.isCreator(authentication.name, #id)")
    public Long deletePointOfInterest(@PathVariable("id") long id) {
       return pointOfInterestService.delete(id);
    }

    @PutMapping(value = "update/{id}")
    @PreAuthorize("@pointOfInterestService.isCreator(authentication.name, #pointOfInterest.id)")
    public ResponseForCreateOrUpdate updatePointOfInterest(@RequestBody PointOfInterest pointOfInterest) {
        return pointOfInterestService.update(pointOfInterest);
    }

    @PostMapping(value = "/{pointId}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addLike(@PathVariable Long pointId, @RequestParam String clientUsername) {
        try {
            return new ResponseEntity<>(pointOfInterestService.addLike(pointId, clientUsername), HttpStatus.CREATED);
        }catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>("Нет такой точки", HttpStatus.NOT_FOUND);
        }
    }
}
