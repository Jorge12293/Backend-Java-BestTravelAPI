package com.example.test.best_travel.infrastructure.abstract_services;

import java.util.Set;

import com.example.test.best_travel.api.models.responses.HotelResponse;

public interface IHotelService extends CatalogService<HotelResponse>{
    
    Set<HotelResponse> readGreaterThan(Integer rating);

}
