package com.event.event.infrastructure.events.repository;


import com.event.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
