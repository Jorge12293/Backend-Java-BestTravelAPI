package com.example.test.best_travel.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationEntity implements Serializable {

    @Id
    private UUID id;
    @Column(name = "date_reservation")
    private LocalDateTime dateTimeReservation;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private Integer totalDays;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name= "hotel_id")
    private HotelEntity hotel;
    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = true)
    private TourEntity tour;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
}
