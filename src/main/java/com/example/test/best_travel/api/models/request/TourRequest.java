package com.example.test.best_travel.api.models.request;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourRequest implements Serializable{
    
    private String customerId;
    private Set<TourFlyRequest> flights;
    private Set<TourHotelRequest> hotels;

}
