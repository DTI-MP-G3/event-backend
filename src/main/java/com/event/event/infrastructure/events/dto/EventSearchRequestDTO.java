package com.event.event.infrastructure.events.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class EventSearchRequestDTO {

    private String venue;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime eventDate;

    private String name;

    @Min(0)
    @Builder.Default
    private Integer page = 0;

    @Min(1)
    @Max(100)
    @Builder.Default
    private Integer size = 10;

    @Pattern(regexp = "^(eventDate|name|venue)$", message = "Sort by must be one of: eventDate, name, location")
    @Builder.Default
    private String sortBy = "eventDate";

    @Pattern(regexp = "^(ASC|DESC|asc|desc)$", message = "Sort direction must be either ASC or DESC")
    @Builder.Default
    private String sortDirection = "desc";  // changed to lowercase

    // Add this method to ensure consistent case
    public String getSortDirection() {
        return sortDirection != null ? sortDirection.toLowerCase() : "desc";
    }
}
