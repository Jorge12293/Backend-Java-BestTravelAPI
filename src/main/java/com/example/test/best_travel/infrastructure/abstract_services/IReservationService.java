package com.example.test.best_travel.infrastructure.abstract_services;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import com.example.test.best_travel.api.models.request.ReservationRequest;
import com.example.test.best_travel.api.models.responses.ReservationResponse;

public interface IReservationService extends CrudService<ReservationRequest,ReservationResponse,UUID>{
    
    BigDecimal findPrice(Long hotelId, Currency currency);

}
