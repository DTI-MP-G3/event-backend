package com.event.event.infrastructure.tickets.repository;

import com.event.event.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value = "SELECT nextval('ticket_sequence')", nativeQuery = true)
    Long getNextTicketId();
}
