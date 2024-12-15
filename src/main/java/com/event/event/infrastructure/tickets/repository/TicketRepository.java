package com.event.event.infrastructure.tickets.repository;

import com.event.event.entity.Ticket.Ticket;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value = "SELECT NEXTVAL('event_mp.tickets_id_seq')", nativeQuery = true)
    Long getNextTicketId();

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT MAX(t.id) FROM Ticket t")
    Long getMaxId();

    List<Ticket> findByUserId(Long userId);
}
