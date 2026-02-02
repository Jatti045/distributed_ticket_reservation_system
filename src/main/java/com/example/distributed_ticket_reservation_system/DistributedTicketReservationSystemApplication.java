package com.example.distributed_ticket_reservation_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DistributedTicketReservationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedTicketReservationSystemApplication.class, args);
    }

}
