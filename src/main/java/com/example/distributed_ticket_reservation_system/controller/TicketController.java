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

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @Valid @RequestBody Ticket ticketDetails) {
        return ticketService.updateTicket(id, ticketDetails);
    }

    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return "Ticket deleted successfully";
    }

    // Endpoint to restore a soft-deleted ticket
    @PostMapping("/{id}/restore")
    public int restoreTicket(@PathVariable Long id) {
        return ticketService.restoreTicket(id);
    }
}
