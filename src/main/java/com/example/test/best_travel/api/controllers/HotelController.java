package com.example.test.best_travel.api.controllers;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.best_travel.api.models.responses.HotelResponse;
import com.example.test.best_travel.infrastructure.abstract_services.IHotelService;
import com.example.test.best_travel.util.SortType;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "hotel")
@AllArgsConstructor
public class HotelController {
   
    private final IHotelService hotelService;


    @GetMapping
    public ResponseEntity<Page<HotelResponse>> getAll(
        @RequestParam Integer page,
        @RequestParam Integer size,
        @RequestHeader(required = false) SortType sortType) {
            if(Objects.isNull(sortType)) sortType = SortType.NONE;
            Page<HotelResponse> response = hotelService.readAll(page, size, sortType);
            return response.isEmpty() 
                ?   ResponseEntity.noContent().build()
                :   ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "less_price")
    public ResponseEntity<Set<HotelResponse>> getLessPrice(@RequestParam BigDecimal price) {
        Set<HotelResponse> response = hotelService.readLessPrice(price);
        return response.isEmpty() 
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "between_price")
    public ResponseEntity<Set<HotelResponse>> getBetweenPrice(
        @RequestParam BigDecimal min,
        @RequestParam BigDecimal max) {
            Set<HotelResponse> response = hotelService.readBetweenPrice(min, max);
            return response.isEmpty()
                ?   ResponseEntity.noContent().build()
                :   ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "greater_than")
    public ResponseEntity<Set<HotelResponse>> getGreaterThan(
        @RequestParam Integer rating
    ){
        Set<HotelResponse> response = hotelService.readGreaterThan(rating);
        return response.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(response);
    }
}
