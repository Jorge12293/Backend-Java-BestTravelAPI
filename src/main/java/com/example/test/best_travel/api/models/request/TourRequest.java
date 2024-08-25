package com.example.test.best_travel.api.models.request;

import java.io.Serializable;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourRequest implements Serializable{
    
    @Size(min = 18,max = 20,message = "The size have to a length between 18 and 20 characters")
    @NotBlank(message = "Id client is mandatory")
    private String customerId;
    @Size(min = 1, message = "Min Flight tour per tour") 
    private Set<TourFlyRequest> flights;
    @Size(min = 1, message = "Min Hotel tour per tour")
    private Set<TourHotelRequest> hotels;
    @Email(message = "Invalid Email")
    private String email;

}
