package com.example.test.best_travel;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.test.best_travel.domain.entities.TicketEntity;
import com.example.test.best_travel.domain.repositories.CustomerRepository;
import com.example.test.best_travel.domain.repositories.FlyRepository;
import com.example.test.best_travel.domain.repositories.HotelRepository;
import com.example.test.best_travel.domain.repositories.ReservationRepository;
import com.example.test.best_travel.domain.repositories.TicketRepository;
import com.example.test.best_travel.domain.repositories.TourRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class BestTravelApplication implements CommandLineRunner{

	
	private final HotelRepository hotelRepository;
	private final FlyRepository flyRepository;
	private final TicketRepository ticketRepository;
	private final ReservationRepository reservationRepository;
	private final TourRepository tourRepository;
	private final CustomerRepository customerRepository; 

	public BestTravelApplication(
		HotelRepository hotelRepository, 
		FlyRepository flyRepository,
		TicketRepository ticketRepository, 
		ReservationRepository reservationRepository,
		TourRepository tourRepository, 
		CustomerRepository customerRepository
	) {
		this.hotelRepository = hotelRepository;
		this.flyRepository = flyRepository;
		this.ticketRepository = ticketRepository;
		this.reservationRepository = reservationRepository;
		this.tourRepository = tourRepository;
		this.customerRepository = customerRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(BestTravelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		var fly = flyRepository.findById(15L).get();
		var hotel = hotelRepository.findById(7L).get();
		var ticket = ticketRepository.findById(UUID.fromString("42345678-1234-5678-5233-567812345678")).get();
		log.info(String.valueOf(fly.toString()));
		log.info(String.valueOf(hotel.toString()));
		log.info(String.valueOf(ticket));
		log.info(String.valueOf(reservationRepository.findById(UUID.fromString("32345678-1234-5678-1234-567812345678")).get()));
		log.info(String.valueOf(customerRepository.findById("VIKI771012HMCRG093").get()));
		*/
		//this.flyRepository.selectLessPrice(BigDecimal.valueOf(20)).forEach(f->System.out.println(f));
		//this.flyRepository.selectBetweenPrice(BigDecimal.valueOf(10),BigDecimal.valueOf(15)).forEach(f->System.out.println(f));
		//this.flyRepository.selectOriginDestiny("Grecia","Mexico").forEach(f->System.out.println(f));
		//var fly = flyRepository.findById(1L).get();
		//fly.getTickets().forEach(ticket->System.out.println(ticket));
		//var fly = flyRepository.findByTicketId(UUID.fromString("42345678-1234-5678-5233-567812345678"));
		//System.out.println(fly);
		//hotelRepository.findByPriceLessThan(BigDecimal.valueOf(100)).forEach(System.out::println);
		//hotelRepository.findByPriceBetween(BigDecimal.valueOf(100),BigDecimal.valueOf(200)).forEach(System.out::println);
		//hotelRepository.findByRatingGreaterThan(3).forEach(System.out::println);
	    //var hotel = hotelRepository.findByReservationsId(UUID.fromString("12345678-1234-5678-1234-567812345678")).get();
		//System.out.println(hotel);
	}

}
