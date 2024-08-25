package com.example.test.best_travel.infrastructure.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.best_travel.api.models.request.TourRequest;
import com.example.test.best_travel.api.models.responses.TourResponse;
import com.example.test.best_travel.domain.entities.CustomerEntity;
import com.example.test.best_travel.domain.entities.FlyEntity;
import com.example.test.best_travel.domain.entities.HotelEntity;
import com.example.test.best_travel.domain.entities.ReservationEntity;
import com.example.test.best_travel.domain.entities.TicketEntity;
import com.example.test.best_travel.domain.entities.TourEntity;
import com.example.test.best_travel.domain.repositories.CustomerRepository;
import com.example.test.best_travel.domain.repositories.FlyRepository;
import com.example.test.best_travel.domain.repositories.HotelRepository;
import com.example.test.best_travel.domain.repositories.TourRepository;
import com.example.test.best_travel.infrastructure.abstract_services.ITourService;
import com.example.test.best_travel.infrastructure.helpers.BlackListHelper;
import com.example.test.best_travel.infrastructure.helpers.CustomerHelper;
import com.example.test.best_travel.infrastructure.helpers.EmailHelper;
import com.example.test.best_travel.infrastructure.helpers.TourHelper;
import com.example.test.best_travel.util.enums.Tables;
import com.example.test.best_travel.util.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class TourService implements ITourService {

    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;
    private final EmailHelper emailHelper;
    
    @Override
    public TourResponse create(TourRequest request) {
        blackListHelper.isInBlackListCustomer(request.getCustomerId());
        CustomerEntity customer = customerRepository.findById(request.getCustomerId()).orElseThrow(()-> new IdNotFoundException("Customer"));

        HashSet<FlyEntity> flights = new HashSet<FlyEntity>();
        request.getFlights().forEach(fly -> flights.add(
                flyRepository.findById(fly.getId()).orElseThrow(()-> new IdNotFoundException("Fly"))));

        HashMap<HotelEntity, Integer> hotels = new HashMap<HotelEntity, Integer>();
        request.getHotels().forEach(hotel -> hotels.put(
                hotelRepository.findById(hotel.getId()).orElseThrow(()-> new IdNotFoundException("Hotel")),
                hotel.getTotalDays()));

        TourEntity tourToSave = TourEntity.builder()
                .tickets(tourHelper.createTickets(flights, customer))
                .reservations(tourHelper.createReservations(hotels, customer))
                .customer(customer)
                .build();

        TourEntity tourSaved = tourRepository.save(tourToSave);

        // Increment Count
        customerHelper.increase(customer.getDni(), TourService.class);

        if(Objects.nonNull(request.getEmail())) emailHelper.sendMail(request.getEmail(), customer.getFullName(), Tables.tour.name());


        return TourResponse.builder()
                .reservationIds(
                        tourSaved.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketIds(tourSaved.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .id(tourSaved.getId())
                .build();
    }

    @Override
    public TourResponse read(Long id) {
        TourEntity toutFromDB = tourRepository.findById(id).orElseThrow(()-> new IdNotFoundException("Tour"));
        return TourResponse.builder()
                .reservationIds(
                        toutFromDB.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketIds(toutFromDB.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .id(toutFromDB.getId())
                .build();
    }

    @Override
    public void delete(Long id) {;
        TourEntity tourToDelete = tourRepository.findById(id).orElseThrow(()-> new IdNotFoundException("Tour"));
        tourRepository.delete(tourToDelete);
    }

    @Override
    public void removeTicket(Long tourId,UUID ticketId) {
        TourEntity tourUpdate = tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException("Tour"));
        tourUpdate.removeTicket(ticketId);
        tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        TourEntity tourUpdate = tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException("Tour"));
        FlyEntity fly = flyRepository.findById(flyId).orElseThrow(()-> new IdNotFoundException("Fly"));
        TicketEntity ticket = tourHelper.createTicket(fly, tourUpdate.getCustomer());
        tourUpdate.addTicket(ticket);
        tourRepository.save(tourUpdate);
        
        return ticket.getId();
    }


    @Override
    public void removeReservation(Long tourId,UUID reservationId) {
        TourEntity tourUpdate = tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException("Tour"));
        tourUpdate.removeReservation(reservationId);
        tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addReservation(Long hotelId, Long tourId, Integer totalDays) {
        TourEntity tourUpdate = tourRepository.findById(tourId).orElseThrow(()-> new IdNotFoundException("Tour"));
        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(()-> new IdNotFoundException("Hotel"));

        ReservationEntity reservation = tourHelper.createReservation(hotel, tourUpdate.getCustomer(),totalDays);
        tourUpdate.addReservation(reservation);
        tourRepository.save(tourUpdate);
        
        return reservation.getId();
    }

}
