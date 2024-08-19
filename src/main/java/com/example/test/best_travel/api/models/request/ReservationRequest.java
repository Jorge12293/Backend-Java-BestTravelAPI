package com.example.test.best_travel.api.models.request;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequest implements Serializable{
    
    @Size(min = 18,max = 20,message = "The size have to a length between 18 and 20 characters.")
    @NotBlank(message = "Id client is mandatory.")
    private String idClient;
    
    @Positive
    @NotNull(message = "Id hotel is mandatory.")
    private Long idHotel;

    @Min(value = 1, message = "Min one days to make reservation.")
    @Max(value = 30, message = "Max 30 days to make reservation.")
    @NotNull(message = "Total days is mandatory.")
    private Integer totalDays;
    
    //@Pattern(regexp = "^(.+)@(.+)$")
    @Email(message = "Invalid Email")
    private String email;
    
}
