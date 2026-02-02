package com.example.distributed_ticket_reservation_system.repository;

import com.example.distributed_ticket_reservation_system.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
