package com.example.test.best_travel.api.models.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourHotelRequest implements Serializable{
    
    private Long id;
    private Integer totalDays;

}
