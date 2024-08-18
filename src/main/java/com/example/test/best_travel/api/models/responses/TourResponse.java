package com.example.test.best_travel.api.models.responses;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourResponse implements Serializable{
    
    private Long id;
    private Set<UUID> ticketIds;
    private Set<UUID> reservationIds;

}
