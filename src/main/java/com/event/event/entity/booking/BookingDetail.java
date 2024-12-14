package com.event.event.entity.booking;

import com.event.event.common.exception.InsufficientTicketsException;
import com.event.event.entity.Ticket.TicketType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;


@Entity
@Getter
@Setter
@Table(name = "booking_details")
public class BookingDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_detail_id_gen")
    @SequenceGenerator(name = "booking_detail_id_gen", sequenceName = "booking_details_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    @NotNull
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

    @NotNull
    @Column(name="quantity")
    private Integer quantity;

    @Column(name = "unit_price",precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
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
    }

    @PreRemove
    protected void onRemove() {
        deletedAt = OffsetDateTime.now();
    }

    public void methodSetSubtotal(){
        if(this.unitPrice != null && this.quantity != null && this.quantity > 0 ){
            this.subtotal= this.unitPrice.multiply(new BigDecimal( this.quantity));
            return;
        }
         else if(this.unitPrice == null ){
            throw  new NullPointerException("unit price can't be null");
        }else if (this.quantity == null ){
            throw  new NullPointerException("quantity can't be null");
        } else if (this.quantity <= 0 ) {
            throw new NumberFormatException("quantity for ticketType can't be 0 ");
        }
    }

}
