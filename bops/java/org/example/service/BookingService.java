package org.example.service;

import jakarta.transaction.Transactional;
import org.example.essence.Ad;
import org.example.essence.Offer;
import org.example.essence.enums_status.BookingStatus;
import org.example.essence.Booking;
import org.example.essence.repository.AdRepository;
import org.example.essence.repository.BookingRepository;
import org.example.essence.repository.OfferRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Service
public class BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepository;
    private final OfferRepository offerRepository;
    private final AdRepository adRepository;
    @Autowired
    private  NotificationService notificationService;

    public BookingService(BookingRepository bookingRepository, OfferRepository offerRepository, AdRepository adRepository) {
        this.bookingRepository = bookingRepository;
        this.offerRepository = offerRepository;
        this.adRepository = adRepository;
    }

    @Transactional
    public Booking createBooking(Booking request) {
        try {
            logger.info("Создание бронирования для запроса: {}", request);
            Ad ad = adRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Объявление не найдено"));
            logger.info("Найдено объявление: {}", ad);

            request.setBookingStatus(BookingStatus.PENDING);
            Booking booking = new Booking();
            booking.setAd(ad);
            booking.setTitle(ad.getTitle());
            booking.setDescription(ad.getDescription());
            booking.setPricePerNight(ad.getPricePerNight());
            booking.setBookingStatus(BookingStatus.PENDING);
            booking.setTimestamp(Instant.now());
            bookingRepository.save(booking);

            notificationService.notifyHost(booking);
            return bookingRepository.save(booking);
        } catch (Exception e) {
            logger.error("Ошибка при создании бронирования: ", e);
            throw e;  // исключение для отката транзакции
        }
    }

    @Transactional
    public boolean cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null && booking.getBookingStatus() == BookingStatus.PENDING) {
            booking.setBookingStatus(BookingStatus.REJECTED);
            bookingRepository.save(booking);
            return true;
        }
        return false;
    }
    @Transactional
    public Offer acceptBookingRequest(Long requestId) {
        logger.info("Обработка запроса на бронирование с id: {}", requestId);


        Booking bookingRequest = bookingRepository.findById(requestId).orElse(null);
        if (bookingRequest == null) {
            logger.error("Бронирование с id {} не найдено.", requestId);
            return null;
        }


        if (bookingRequest.getBookingStatus() == BookingStatus.PENDING) {
            logger.info("Запрос на бронирование с id {} в статусе PENDING. Меняем статус на SENT.", requestId);
            bookingRequest.setBookingStatus(BookingStatus.SENT);
            bookingRepository.save(bookingRequest);

            Offer offer = new Offer(bookingRequest);
            offer = offerRepository.save(offer);
            logger.info("Оффер создан с id: {}, для запроса на бронирование с id: {}", offer.getId(), offer.getBookingRequest().getId());


            return offer;
        } else {
            logger.error("Запрос на бронирование с id {} не в статусе PENDING, текущий статус: {}", requestId, bookingRequest.getBookingStatus());
            return null;
        }
    }


    @Transactional
    public void cancelExpiredOffers() {
        // все предложения, которые были созданы более 24 часов назад
        List<Offer> expiredOffers = offerRepository.findAllBySentAtBefore(
                LocalDateTime.ofInstant(Instant.now().minusSeconds(86400), ZoneId.systemDefault())
        );
        for (Offer offer : expiredOffers) {
            if (offer.getBookingRequest().getBookingStatus() == BookingStatus.PENDING) {
                // отмена предложений, которые не были подтверждены
                offer.getBookingRequest().setBookingStatus(BookingStatus.REJECTED);
                offerRepository.save(offer);
                System.out.println("Оффер истек, и запрос на бронирование отклонен: " + offer.getBookingRequest().getId());
            }
        }
    }
    @Transactional
    public Booking rebook(Long oldBookingId) {
        Booking oldBooking = bookingRepository.findById(oldBookingId)
                .orElseThrow(() -> new RuntimeException("Предыдущее бронирование не найдено"));

        if (oldBooking.getBookingStatus() != BookingStatus.EXPIRED) {
            throw new IllegalStateException("Только просроченные бронирования могут быть повторно забронированы");
        }

        LocalDateTime lastAttempt = LocalDateTime.ofInstant(oldBooking.getTimestamp(), ZoneId.systemDefault());
        if (lastAttempt.plusHours(24).isAfter(LocalDateTime.now())) {
            throw new IllegalStateException("Необходимо подождать 24 часа перед повторной попыткой бронирования");
        }


        Booking newBooking = new Booking();
        newBooking.setTitle(oldBooking.getTitle());
        newBooking.setDescription(oldBooking.getDescription());
        newBooking.setPricePerNight(oldBooking.getPricePerNight());
        newBooking.setBookingStatus(BookingStatus.PENDING);
        newBooking.setTimestamp(Instant.now());

        Booking saved = bookingRepository.save(newBooking);
        notificationService.notifyHost(saved);
        return saved;
    }
}
