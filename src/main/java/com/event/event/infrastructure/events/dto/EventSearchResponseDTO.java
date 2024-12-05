package com.event.event.infrastructure.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventSearchResponseDTO {
    private String name;
    private String venue;
    private OffsetDateTime eventDate;


}
