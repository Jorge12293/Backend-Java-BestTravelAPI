package com.example.test.best_travel.infrastructure.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.best_travel.api.models.request.ReservationRequest;
import com.example.test.best_travel.api.models.responses.HotelResponse;
import com.example.test.best_travel.api.models.responses.ReservationResponse;
import com.example.test.best_travel.domain.entities.CustomerEntity;
import com.example.test.best_travel.domain.entities.HotelEntity;
import com.example.test.best_travel.domain.entities.ReservationEntity;
import com.example.test.best_travel.domain.repositories.CustomerRepository;
import com.example.test.best_travel.domain.repositories.HotelRepository;
import com.example.test.best_travel.domain.repositories.ReservationRepository;
import com.example.test.best_travel.infrastructure.abstract_services.IReservationService;
import com.example.test.best_travel.infrastructure.helpers.BlackListHelper;
import com.example.test.best_travel.infrastructure.helpers.CustomerHelper;
import com.example.test.best_travel.util.enums.Tables;
import com.example.test.best_travel.util.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerHelper customerHelper;
    private final BlackListHelper blackListHelper;

    @Override
    public ReservationResponse create(ReservationRequest request) {
        blackListHelper.isInBlackListCustomer(request.getIdClient());        

        HotelEntity hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow(()-> new IdNotFoundException(Tables.hotel.name()));
        CustomerEntity customer = customerRepository.findById(request.getIdClient()).orElseThrow(()-> new IdNotFoundException(Tables.customer.name()));

        ReservationEntity reservationToPersist = ReservationEntity.builder()
            .id(UUID.randomUUID())
            .hotel(hotel)
            .customer(customer)
            .totalDays(request.getTotalDays())
            .dateTimeReservation(LocalDateTime.now())
            .dateStart(LocalDate.now())
            .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
            .price(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)))
            .build();
        
        ReservationEntity reservationPersisted = reservationRepository.save(reservationToPersist);    
        log.info("Reservation saved with id: {}",reservationPersisted.getId());   
        
        // Increment Count
        customerHelper.increase(customer.getDni(), ReservationService.class);
        return entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID id) {
        ReservationEntity reservationFromDB = reservationRepository.findById(id).orElseThrow(()-> new IdNotFoundException(Tables.reservation.name()));
        return entityToResponse(reservationFromDB);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID id) {
        HotelEntity hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow(()-> new IdNotFoundException(Tables.hotel.name()));
        ReservationEntity reservationToUpdate = reservationRepository.findById(id).orElseThrow(()-> new IdNotFoundException(Tables.reservation.name()));

        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setTotalDays(request.getTotalDays());
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
        reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)));
        
        ReservationEntity reservationUpdated = reservationRepository.save(reservationToUpdate);    
        log.info("Reservation updated with id: {}",reservationUpdated.getId());   

        return entityToResponse(reservationUpdated);
    }

    @Override
    public void delete(UUID id) {
        ReservationEntity reservationToDelete = reservationRepository.findById(id).orElseThrow(()-> new IdNotFoundException(Tables.reservation.name()));
        reservationRepository.delete(reservationToDelete);
    }
    
    @Override
    public BigDecimal findPrice(Long hotelId) {
        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(()-> new IdNotFoundException(Tables.hotel.name()));
        return hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage));
    }

    private ReservationResponse entityToResponse(ReservationEntity entity){
        
        ReservationResponse reservationResponse = new ReservationResponse();
        BeanUtils.copyProperties(entity, reservationResponse);

        HotelResponse hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(), hotelResponse);

        reservationResponse.setHotel(hotelResponse);
        return reservationResponse;
    }

    public static final BigDecimal charges_price_percentage = BigDecimal.valueOf(0.20);

}
