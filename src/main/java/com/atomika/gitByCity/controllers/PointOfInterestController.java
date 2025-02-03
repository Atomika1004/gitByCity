package com.atomika.gitByCity.controllers;

import com.atomika.gitByCity.dto.PointOfInterest;
import com.atomika.gitByCity.dto.ResponseForCreateOrUpdate;
import com.atomika.gitByCity.service.PointOfInterestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/base/points")
public class PointOfInterestController {

    private final PointOfInterestService pointOfInterestService;

    @GetMapping()
    public ResponseEntity<List<PointOfInterest>> getAllPointsOfInterest(){
        pointOfInterestService.asyncLog();
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
    public ResponseEntity<?> createPointOfInterest(@RequestBody PointOfInterest pointOfInterest) {
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
    public ResponseEntity<ResponseForCreateOrUpdate> updatePointOfInterest(@RequestBody PointOfInterest pointOfInterest) {
        return  new ResponseEntity<>(pointOfInterestService.update(pointOfInterest), HttpStatus.OK);
    }

    @PostMapping(value = "/{pointId}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addLike(@PathVariable Long pointId, @RequestParam String clientUsername) throws ExecutionException, InterruptedException, TimeoutException {
        try {
            CompletableFuture<Long> resLike = pointOfInterestService.addLike(pointId, clientUsername);
            return new ResponseEntity<>(resLike.get(500, TimeUnit.MILLISECONDS), HttpStatus.OK);
        }catch (TimeoutException e) {
            return new ResponseEntity<>("Сервис не успел отработать за должное время", HttpStatus.REQUEST_TIMEOUT);
        }
    }
}
