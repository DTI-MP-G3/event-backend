package com.event.event.entity.Ticket;

import com.event.event.common.exception.InsufficientTicketsException;
import com.event.event.entity.Event;
import com.event.event.enums.EventStatus;
import com.event.event.enums.TicketTypeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(name="ticket_types")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_type_id_gen")
    @SequenceGenerator(name = "ticket_type_id_gen", sequenceName = "ticket_types_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Size(max= 100)
    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name="price",precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull
    @Column(name="quantity")
    private Integer quantity;

    @NotNull
    @Column(name ="quantity_available")
    private Integer quantityAvailable;

    @Column(name="description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private TicketTypeStatus status = TicketTypeStatus.AVAILABLE;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
        if(quantityAvailable == 0){
            status = TicketTypeStatus.SOLD_OUT;
        }
    }

    @PreRemove
    protected void onRemove() {
        deletedAt = OffsetDateTime.now();
    }

    public void decreaseAvailable(int amount) {
        if (this.quantityAvailable < amount) {
            throw new InsufficientTicketsException("Not enough tickets available");
        }
        this.quantityAvailable -= amount;
    }

}
