package com.example.distributed_ticket_reservation_system.service;

import com.example.distributed_ticket_reservation_system.model.Booking;
import com.example.distributed_ticket_reservation_system.model.Ticket;
import com.example.distributed_ticket_reservation_system.repository.BookingRepository;
import com.example.distributed_ticket_reservation_system.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    public List<Booking> getBookingsByEmail(String email) {
        return bookingRepository.findByUserEmail(email);
    }

    public List<Booking> getBookingsByTicketId(Long ticketId) {
        return bookingRepository.findByTicketId(ticketId);
    }

    public Booking updateBooking(Long id, Booking bookingDetails) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        // Update booking details
        booking.setUserEmail(bookingDetails.getUserEmail());
        if (bookingDetails.getTicketId() != null) {
            booking.setTicketId(bookingDetails.getTicketId());
        }
        if (bookingDetails.getBookingTime() != null) {
            booking.setBookingTime(bookingDetails.getBookingTime());
        }

        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }
}
