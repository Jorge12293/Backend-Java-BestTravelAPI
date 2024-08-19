package com.example.test.best_travel.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.best_travel.api.models.responses.FlyResponse;
import com.example.test.best_travel.infrastructure.abstract_services.IFlyService;
import com.example.test.best_travel.util.enums.SortType;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "fly")
@AllArgsConstructor
public class FlyController {
    
    private final IFlyService flyService;

    @GetMapping
    public ResponseEntity<Page<FlyResponse>> getAll(
        @RequestParam Integer page,
        @RequestParam Integer size,
        @RequestHeader(required = false) SortType sortType) {
            if(Objects.isNull(sortType)) sortType = SortType.NONE;
            Page<FlyResponse> response = flyService.readAll(page, size, sortType);
            return response.isEmpty() 
                ?   ResponseEntity.noContent().build()
                :   ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "less_price")
    public ResponseEntity<Set<FlyResponse>> getLessPrice(@RequestParam BigDecimal price) {
        Set<FlyResponse> response = flyService.readLessPrice(price);
        return response.isEmpty() 
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "between_price")
    public ResponseEntity<Set<FlyResponse>> getBetweenPrice(
        @RequestParam BigDecimal min,
        @RequestParam BigDecimal max) {
            Set<FlyResponse> response = flyService.readBetweenPrice(min, max);
            return response.isEmpty()
                ?   ResponseEntity.noContent().build()
                :   ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "origin_destiny")
    public ResponseEntity<Set<FlyResponse>> getByOriginDestiny(
        @RequestParam String origin,
        @RequestParam String destiny
    ){
        Set<FlyResponse> response = flyService.readByOriginDestiny(origin, destiny);
        return response.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(response);
    }
    


}
