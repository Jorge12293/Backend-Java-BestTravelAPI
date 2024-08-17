package com.example.test.best_travel.infraestructure.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.best_travel.api.models.request.TicketRequest;
import com.example.test.best_travel.api.models.responses.FlyResponse;
import com.example.test.best_travel.api.models.responses.TicketResponse;
import com.example.test.best_travel.domain.entities.CustomerEntity;
import com.example.test.best_travel.domain.entities.FlyEntity;
import com.example.test.best_travel.domain.entities.TicketEntity;
import com.example.test.best_travel.domain.repositories.CustomerRepository;
import com.example.test.best_travel.domain.repositories.FlyRepository;
import com.example.test.best_travel.domain.repositories.TicketRepository;
import com.example.test.best_travel.infraestructure.abstract_services.ITicketService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketService implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    
    @Override
    public TicketResponse create(TicketRequest request) {
        FlyEntity fly = flyRepository.findById(request.getIdFly()).orElseThrow();
        CustomerEntity customer = customerRepository.findById(request.getIdClient()).orElseThrow();
        TicketEntity ticketToPersist = TicketEntity.builder()
            .id(UUID.randomUUID())
            .fly(fly)
            .customer(customer)
            .price(fly.getPrice().multiply(BigDecimal.valueOf(0.25)))
            .purchaseDate(LocalDate.now())
            .arrivalDate(LocalDateTime.now())
            .departureDate(LocalDateTime.now())
            .build();
        
        TicketEntity ticketPersisted = ticketRepository.save(ticketToPersist);
        log.info("Ticket saved with id: {}",ticketPersisted.getId());    
        return entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
    private TicketResponse entityToResponse(TicketEntity entity){
        TicketResponse ticketResponse = new TicketResponse();
        BeanUtils.copyProperties(entity, ticketResponse);
        FlyResponse flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);

        ticketResponse.setFly(flyResponse);
        return ticketResponse;
    }
}
