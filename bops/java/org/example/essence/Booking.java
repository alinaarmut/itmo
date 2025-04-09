package org.example.essence;


import jakarta.persistence.*;
import lombok.*;
import org.example.essence.enums_status.BookingStatus;
import org.example.essence.enums_status.OfferStatus;


import java.time.Instant;


@Data
@Entity
@Table(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

//бронирование
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Double pricePerNight;


    private Long hostId;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private Instant timestamp;

    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ad_id")
    private Ad ad;


    @Version
    private Long version;
}
