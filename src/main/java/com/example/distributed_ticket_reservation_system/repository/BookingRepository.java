package com.example.distributed_ticket_reservation_system.repository;

import com.example.distributed_ticket_reservation_system.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserEmail(String userEmail);
    List<Booking> findByTicketId(Long ticketId);
}
