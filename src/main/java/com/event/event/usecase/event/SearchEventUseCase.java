package com.event.event.usecase.event;

import com.event.event.infrastructure.events.dto.EventSearchResponseDTO;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;

public interface SearchEventUseCase {
//    Page<EventProjection> searchEventsByFilters(EventSearchRequestDTO req);
    List<EventSearchResponseDTO> searchEvents(String name, String venue, OffsetDateTime eventDate);
    Page<EventSearchResponseDTO> searchEvents(
            @Nullable String name,
            @Nullable String venue,
            @Nullable OffsetDateTime eventDate,
            Pageable pageable
    );
}
