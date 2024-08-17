package com.example.test.best_travel.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.best_travel.api.models.request.TicketRequest;
import com.example.test.best_travel.api.models.responses.TicketResponse;
import com.example.test.best_travel.infraestructure.abstract_services.ITicketService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "ticket")
@AllArgsConstructor
public class TicketController {
    
    private final ITicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> post(@RequestBody TicketRequest request) {
        return ResponseEntity.ok(ticketService.create(request));
    }
    

}
