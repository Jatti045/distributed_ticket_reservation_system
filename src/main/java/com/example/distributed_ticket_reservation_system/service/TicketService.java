package com.example.distributed_ticket_reservation_system.service;

import jakarta.transaction.Transactional;
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

    @Transactional
    public int restoreTicket(Long id) {
        int updated = ticketRepository.restoreById(id);

        if (updated == 0) {
            throw new RuntimeException("Ticket not found or already restored");
        }

        return updated;
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket updateTicket(Long id, Ticket updatedDetails) {
        // Find ticket or throw error if it doesn't exist
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setEventName(updatedDetails.getEventName());
        ticket.setPrice(updatedDetails.getPrice());
        ticket.setAvailableQuantity(updatedDetails.getAvailableQuantity());

        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. Ticket not found with id: " + id);
        }

        ticketRepository.deleteById(id);
    }
}
