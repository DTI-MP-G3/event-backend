package com.event.event.infrastructure.tickets.repository;

import com.event.event.entity.Ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value = "SELECT NEXTVAL('event_mp.tickets_id_seq')", nativeQuery = true)
    Long getNextTicketId();

    @Query(value= "SELECT MAX(id) from event_mp.tickets",nativeQuery = true)
    Long getMaxId();
}
