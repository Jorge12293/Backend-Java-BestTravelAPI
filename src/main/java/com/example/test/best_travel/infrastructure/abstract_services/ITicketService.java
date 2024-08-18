package com.example.test.best_travel.infrastructure.abstract_services;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.test.best_travel.api.models.request.TicketRequest;
import com.example.test.best_travel.api.models.responses.TicketResponse;

public interface ITicketService extends CrudService<TicketRequest,TicketResponse,UUID>{

    BigDecimal findPrice(Long flyId);
    
} 
