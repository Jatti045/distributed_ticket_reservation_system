package com.example.distributed_ticket_reservation_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.distributed_ticket_reservation_system.model.Ticket;
import org.springframework.web.bind.annotation.*;
import com.example.distributed_ticket_reservation_system.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public Ticket addTicket(@Valid @RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @GetMapping
    public List<Ticket> getTickets() {
        return ticketService.getAllTickets();
    }
}
