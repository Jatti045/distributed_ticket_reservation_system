package com.example.distributed_ticket_reservation_system.service;

import lombok.RequiredArgsConstructor;
import com.example.distributed_ticket_reservation_system.model.Ticket;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.example.distributed_ticket_reservation_system.repository.TicketRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    // @Cachable: If the ticket is in Redis, return it. If not, hit DB and save to Redis.
    @Cacheable(value = "tickets", key = "#id")
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    // @CacheEvict: If we update a ticket, remove the "old" data from the Redis so it's not stale
    @CacheEvict(value = "tickets", key = "#ticket.id")
    public Ticket updateTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
