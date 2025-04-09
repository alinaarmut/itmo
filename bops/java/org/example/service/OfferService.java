package org.example.service;

import jakarta.transaction.Transactional;
import org.example.essence.Booking;
import org.example.essence.Offer;
import org.example.essence.enums_status.BookingStatus;
import org.example.essence.enums_status.OfferStatus;
import org.example.essence.repository.BookingRepository;
import org.example.essence.repository.OfferRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final BookingRepository bookingRepository;

    public OfferService(OfferRepository offerRepository, BookingRepository bookingRequestRepository) {
        this.offerRepository = offerRepository;
        this.bookingRepository = bookingRequestRepository;
    }


    @Transactional
    public String acceptOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElse(null);

        if (offer == null) {
            return "Оффер не найден";
        }
        if (offer.getStatus() != OfferStatus.SENT) {
            return "Offer cannot be accepted, current status: " + offer.getStatus();
        }


        offer.setStatus(OfferStatus.CONFIRMED);
        offer.setConfirmedAt(LocalDateTime.now());
        offerRepository.save(offer);


        Booking bookingRequest = offer.getBookingRequest();
        bookingRequest.setBookingStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(bookingRequest);

        return "Offer accepted and booking status updated to CONFIRMED";
    }

    // Отклонение оффера и изменение статуса заявки на REJECTED
    @Transactional
    public String rejectOffer(Long offerId) {

        Offer offer = offerRepository.findById(offerId).orElse(null);

        if (offer == null) {
            return "Оффер не найден";
        }


        offer.setStatus(OfferStatus.REJECTED);
        offerRepository.save(offer);

        Booking bookingRequest = offer.getBookingRequest();
        bookingRequest.setBookingStatus(BookingStatus.REJECTED);
        bookingRepository.save(bookingRequest);

        return "Offer rejected and booking status updated to REJECTED";
    }
}
