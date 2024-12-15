package com.event.event.infrastructure.events.repository;


import com.event.event.entity.Event;
import com.event.event.enums.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    List<Event> findByOrganizerId(Long organizerId);
    Optional<Event> findByIdAndOrganizerId(Integer id, Long organizerId);

    List<Event> findAllByStatus(EventStatus status);
    // or with pagination
    Page<Event> findAllByStatus(EventStatus status, Pageable pageable);
}
