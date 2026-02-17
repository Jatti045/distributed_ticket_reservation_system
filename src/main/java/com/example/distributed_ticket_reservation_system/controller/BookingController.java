package com.example.distributed_ticket_reservation_system.controller;

import com.example.distributed_ticket_reservation_system.model.Booking;
import com.example.distributed_ticket_reservation_system.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Map<String, String> request) {
        Long ticketId = Long.parseLong(request.get("ticketId"));
        String email = request.get("email");
        Booking booking = bookingService.bookTicket(ticketId, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Booking>> getBookingsByEmail(@PathVariable String email) {
        List<Booking> bookings = bookingService.getBookingsByEmail(email);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<Booking>> getBookingsByTicketId(@PathVariable Long ticketId) {
        List<Booking> bookings = bookingService.getBookingsByTicketId(ticketId);
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @Valid @RequestBody Booking bookingDetails) {
        Booking updatedBooking = bookingService.updateBooking(id, bookingDetails);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("Booking deleted successfully");
    }
}

