package com.example.test.best_travel.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.best_travel.api.models.request.TourRequest;
import com.example.test.best_travel.api.models.responses.TourResponse;
import com.example.test.best_travel.infrastructure.abstract_services.ITourService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping(path = "tour")
@AllArgsConstructor
public class TourController {
    
    private final ITourService tourService;

    @PostMapping
    public ResponseEntity<TourResponse> create(@RequestBody TourRequest request) {
        return ResponseEntity.ok(tourService.create(request));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<TourResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(tourService.read(id));
    }
    
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
