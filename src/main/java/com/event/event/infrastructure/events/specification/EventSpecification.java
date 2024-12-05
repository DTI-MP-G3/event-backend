package com.event.event.infrastructure.events.specification;

import com.event.event.entity.Event;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventSpecification {

    public static Specification<Event> searchEvents(String name, String venue, OffsetDateTime eventDate) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Add name search condition if name is provided
            if (name != null && !name.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                ));
            }

            // Add venue search condition if venue is provided
            if (venue != null && !venue.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("venue")),
                        "%" + venue.toLowerCase() + "%"
                ));
            }

            // Add eventDate search condition if eventDate is provided
            if (eventDate != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("eventDate"),
                        eventDate
                ));
            }

            // Add condition to exclude deleted events
            predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}