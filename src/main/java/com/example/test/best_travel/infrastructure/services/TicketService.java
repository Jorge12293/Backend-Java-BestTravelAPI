package com.example.test.best_travel.infrastructure.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.best_travel.api.models.request.TicketRequest;
import com.example.test.best_travel.api.models.responses.FlyResponse;
import com.example.test.best_travel.api.models.responses.TicketResponse;
import com.example.test.best_travel.domain.entities.jpa.CustomerEntity;
import com.example.test.best_travel.domain.entities.jpa.FlyEntity;
import com.example.test.best_travel.domain.entities.jpa.TicketEntity;
import com.example.test.best_travel.domain.repositories.jpa.CustomerRepository;
import com.example.test.best_travel.domain.repositories.jpa.FlyRepository;
import com.example.test.best_travel.domain.repositories.jpa.TicketRepository;
import com.example.test.best_travel.infrastructure.abstract_services.ITicketService;
import com.example.test.best_travel.infrastructure.helpers.BlackListHelper;
import com.example.test.best_travel.infrastructure.helpers.CustomerHelper;
import com.example.test.best_travel.infrastructure.helpers.EmailHelper;
import com.example.test.best_travel.util.BestTravelUtil;
import com.example.test.best_travel.util.enums.Tables;
import com.example.test.best_travel.util.exceptions.IdNotFoundException;

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
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;
    private final EmailHelper emailHelper;
    
    @Override
    public TicketResponse create(TicketRequest request) {
        blackListHelper.isInBlackListCustomer(request.getIdClient());
        FlyEntity fly = flyRepository.findById(request.getIdFly()).orElseThrow(()-> new IdNotFoundException("Fly"));
        CustomerEntity customer = customerRepository.findById(request.getIdClient()).orElseThrow(()-> new IdNotFoundException("Customer"));
        TicketEntity ticketToPersist = TicketEntity.builder()
            .id(UUID.randomUUID())
            .fly(fly)
            .customer(customer)
            .price(fly.getPrice().add(fly.getPrice().multiply(charges_price_percentage)))
            .purchaseDate(LocalDate.now())
            .departureDate(BestTravelUtil.getRandomSoon())
            .arrivalDate(BestTravelUtil.getRandomLatter() )
            .build();
        
        TicketEntity ticketPersisted = ticketRepository.save(ticketToPersist);
        log.info("Ticket saved with id: {}",ticketPersisted.getId());    
        
        if(Objects.nonNull(request.getEmail())) emailHelper.sendMail(request.getEmail(), customer.getFullName(), Tables.ticket.name());

        // Increment Count
        customerHelper.increase(customer.getDni(), TicketService.class);

        return entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID id) {
        TicketEntity ticketFromDb = ticketRepository.findById(id).orElseThrow(()-> new IdNotFoundException("Ticket"));
        return entityToResponse(ticketFromDb);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID id) {
        TicketEntity ticketToUpdated = ticketRepository.findById(id).orElseThrow(()-> new IdNotFoundException("Ticket"));
        FlyEntity fly = flyRepository.findById(request.getIdFly()).orElseThrow(()-> new IdNotFoundException("Fly"));

        ticketToUpdated.setFly(fly);
        ticketToUpdated.setPrice(fly.getPrice().add(fly.getPrice().multiply(charges_price_percentage)));
        ticketToUpdated.setDepartureDate(BestTravelUtil.getRandomSoon());
        ticketToUpdated.setArrivalDate(BestTravelUtil.getRandomLatter());

        TicketEntity ticketUpdated = ticketRepository.save(ticketToUpdated);
        log.info("Ticket updated with id {}",ticketUpdated);
        return entityToResponse(ticketUpdated);
    }

    @Override
    public void delete(UUID id) {
        TicketEntity ticketToDeleted = ticketRepository.findById(id).orElseThrow(()-> new IdNotFoundException("Ticket"));
        ticketRepository.delete(ticketToDeleted);
    }
    
    @Override
    public BigDecimal findPrice(Long flyId) {
        FlyEntity fly = flyRepository.findById(flyId).orElseThrow(()-> new IdNotFoundException("Fly"));
        return fly.getPrice().add(fly.getPrice().multiply(charges_price_percentage));
    }


    private TicketResponse entityToResponse(TicketEntity entity){

        TicketResponse ticketResponse = new TicketResponse();
        BeanUtils.copyProperties(entity, ticketResponse);
        
        FlyResponse flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);

        ticketResponse.setFly(flyResponse);
        return ticketResponse;
    }

    public static final BigDecimal charges_price_percentage = BigDecimal.valueOf(0.25);
}
