package com.example.distributed_ticket_reservation_system.service;

import com.example.distributed_ticket_reservation_system.model.Booking;
import com.example.distributed_ticket_reservation_system.model.Ticket;
import com.example.distributed_ticket_reservation_system.repository.BookingRepository;
import com.example.distributed_ticket_reservation_system.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;

    @Transactional // Ensure if anything fails, database rolls back
    public Booking bookTicket(Long ticketId, String email) {
        // Lock the ticket so no one else can change the quantity
        Ticket ticket = ticketRepository.findByIdWithLock(ticketId)
                .orElseThrow(() -> new RuntimeException(("Ticket not found")));

        // Check if ticket still has quantity
        if (ticket.getAvailableQuantity() <= 0) {
            throw new RuntimeException("Sold out!");
        }

        // Decrease quantity
        ticket.setAvailableQuantity((ticket.getAvailableQuantity() - 1));
        ticketRepository.save(ticket);

        // Create the booking record
        Booking booking = Booking.builder()
                .ticketId(ticketId)
                .userEmail(email)
                .bookingTime(LocalDateTime.now())
                .build();

        return bookingRepository.save(booking);
    }
}
