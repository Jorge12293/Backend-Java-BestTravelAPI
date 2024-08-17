package com.example.test.best_travel.api.models.responses;

import java.math.BigDecimal;

import com.example.test.best_travel.util.AeroLine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlyResponse {
    
    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinyLat;
    private Double destinyLng;
    private String originName;
    private String destinyName;
    private BigDecimal price;
    private AeroLine aeroLine;

}
