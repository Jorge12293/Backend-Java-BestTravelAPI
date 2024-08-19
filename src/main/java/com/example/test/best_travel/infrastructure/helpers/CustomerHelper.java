package com.example.test.best_travel.infrastructure.helpers;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.best_travel.domain.entities.CustomerEntity;
import com.example.test.best_travel.domain.repositories.CustomerRepository;
import com.example.test.best_travel.util.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Transactional
@Component
@AllArgsConstructor
public class CustomerHelper {

    private final CustomerRepository customerRepository;

    public void increase(String customerId, Class<?> type) {
        CustomerEntity customerToUpdate = customerRepository.findById(customerId).orElseThrow(()-> new IdNotFoundException("Customer"));
        switch (type.getSimpleName()) {
            case "TourService" -> customerToUpdate.setTotalTours(customerToUpdate.getTotalTours() + 1);
            case "TicketService" -> customerToUpdate.setTotalFlights(customerToUpdate.getTotalFlights() + 1);
            case "ReservationService" -> customerToUpdate.setTotalLodgings(customerToUpdate.getTotalLodgings() + 1);
        }
        customerRepository.save(customerToUpdate);
    }

}
