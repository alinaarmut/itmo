package org.example.essence;


// оффер от хоста

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.example.essence.enums_status.OfferStatus;


import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "offer")
@Data
public class Offer {

    @Id @GeneratedValue
    private Long id;


    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @Getter
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking bookingRequest;

    private LocalDateTime sentAt;
    private LocalDateTime confirmedAt;
    public Offer(Booking bookingRequest) {
        this.bookingRequest = bookingRequest;
        this.status = OfferStatus.SENT;
        this.sentAt = LocalDateTime.now();
    }


    public Offer() {}


}
