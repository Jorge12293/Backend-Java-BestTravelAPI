package com.example.test.best_travel.infrastructure.abstract_services;

import java.util.Set;

import com.example.test.best_travel.api.models.responses.FlyResponse;

public interface IFlyService extends CatalogService<FlyResponse>{
    
    Set<FlyResponse> readByOriginDestiny(String origin,String destiny);

}
