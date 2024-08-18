package com.example.test.best_travel.infrastructure.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.best_travel.domain.entities.CustomerEntity;
import com.example.test.best_travel.domain.entities.FlyEntity;
import com.example.test.best_travel.domain.entities.HotelEntity;
import com.example.test.best_travel.domain.entities.ReservationEntity;
import com.example.test.best_travel.domain.entities.TicketEntity;
import com.example.test.best_travel.domain.repositories.ReservationRepository;
import com.example.test.best_travel.domain.repositories.TicketRepository;
import com.example.test.best_travel.infrastructure.services.ReservationService;
import com.example.test.best_travel.infrastructure.services.TicketService;
import com.example.test.best_travel.util.BestTravelUtil;

import lombok.AllArgsConstructor;

@Transactional
@Component
@AllArgsConstructor
public class TourHelper {

    private TicketRepository ticketRepository;
    private ReservationRepository reservationRepository;

    public Set<TicketEntity> createTickets(Set<FlyEntity> flights, CustomerEntity customer) {
        HashSet<TicketEntity> response = new HashSet<TicketEntity>(flights.size());
        flights.forEach(fly -> {
            TicketEntity ticketToPersist = TicketEntity.builder()
                    .id(UUID.randomUUID())
                    .fly(fly)
                    .customer(customer)
                    .price(fly.getPrice().add(fly.getPrice().multiply(TicketService.charges_price_percentage)))
                    .purchaseDate(LocalDate.now())
                    .departureDate(BestTravelUtil.getRandomSoon())
                    .arrivalDate(BestTravelUtil.getRandomLatter())
                    .build();
            response.add(ticketRepository.save(ticketToPersist));
        });
        return response;
    }

    public Set<ReservationEntity> createReservations(HashMap<HotelEntity, Integer> hotels, CustomerEntity customer) {
        HashSet<ReservationEntity> response = new HashSet<ReservationEntity>(hotels.size());
        hotels.forEach((hotel, totalDays) -> {
            ReservationEntity reservationToPersist = ReservationEntity.builder()
                    .id(UUID.randomUUID())
                    .hotel(hotel)
                    .customer(customer)
                    .totalDays(totalDays)
                    .dateTimeReservation(LocalDateTime.now())
                    .dateStart(LocalDate.now())
                    .dateEnd(LocalDate.now().plusDays(totalDays))
                    .price(hotel.getPrice().add(hotel.getPrice().multiply(ReservationService.charges_price_percentage)))
                    .build();
            response.add(reservationRepository.save(reservationToPersist));
        });
        return response;
    }

    public TicketEntity createTicket(FlyEntity fly, CustomerEntity customer) {
        TicketEntity ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().add(fly.getPrice().multiply(TicketService.charges_price_percentage)))
                .purchaseDate(LocalDate.now())
                .departureDate(BestTravelUtil.getRandomSoon())
                .arrivalDate(BestTravelUtil.getRandomLatter())
                .build();
        return ticketRepository.save(ticketToPersist);
    }
}
