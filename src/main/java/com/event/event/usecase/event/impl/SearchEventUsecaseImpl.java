package com.event.event.usecase.event.impl;

import com.event.event.common.exception.DataNotFoundException;
import com.event.event.entity.Event;
import com.event.event.infrastructure.events.dto.EventSearchResponseDTO;
import com.event.event.infrastructure.events.repository.EventRepository;
import com.event.event.infrastructure.events.specification.EventSpecification;
import com.event.event.infrastructure.events.util.EventSearchResponseMapper;
import com.event.event.usecase.event.SearchEventUseCase;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SearchEventUsecaseImpl implements SearchEventUseCase {

    private final EventRepository eventRepository;
    private final EventSearchResponseMapper eventSearchResponseMapper;

    public SearchEventUsecaseImpl(EventRepository eventRepository, EventSearchResponseMapper eventSearchResponseMapper) {
        this.eventRepository = eventRepository;
        this.eventSearchResponseMapper = eventSearchResponseMapper;
    }


    @Override
    public List<EventSearchResponseDTO> searchEvents(@Nullable String name,
                                                     @Nullable String venue,
                                                     @Nullable OffsetDateTime eventDate) {
        Specification<Event> spec = EventSpecification.searchEvents(name, venue, eventDate);
        List<Event> events = eventRepository.findAll(spec);
        return eventSearchResponseMapper.toDTOList(events);
    }

    @Override
    public Page<EventSearchResponseDTO> searchEvents(
            @Nullable String name,
            @Nullable String venue,
            @Nullable OffsetDateTime eventDate,
            Pageable pageable
    ) {
        Specification<Event> spec = EventSpecification.searchEvents(name, venue, eventDate);
        Page<Event> events = eventRepository.findAll(spec, pageable);
        return eventSearchResponseMapper.toDTOPage(events);
    }

    @Override
    public Optional<Event> findEventById (Integer id){
        return eventRepository.findById(id);
    }

}