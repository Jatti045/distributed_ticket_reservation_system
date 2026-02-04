package com.example.distributed_ticket_reservation_system.repository;

import com.example.distributed_ticket_reservation_system.model.Ticket;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT * FROM tickets WHERE id = :id", nativeQuery = true)
    Optional<Ticket> findByIdWithLock(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tickets SET deleted = false WHERE id = :id", nativeQuery = true)
    int restoreById(@Param("id") Long id);
}
