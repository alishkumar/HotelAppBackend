package com.hotel.booking.service;

import com.hotel.booking.dto.CreateBookingRequest;
import com.hotel.booking.dto.UpdateBookingRequest;
import com.hotel.booking.dto.BookingResponse;
import com.hotel.booking.dto.BookingSearchCriteria;
import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.BookingStatus;
import com.hotel.booking.entity.PaymentType;
import com.hotel.booking.repository.BookingRepository;
import com.hotel.booking.repository.BookingSpecification;
import com.hotel.common.exception.InvalidBookingException;
import com.hotel.common.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final Clock clock;

    public BookingService(BookingRepository bookingRepository, Clock clock) {
        this.bookingRepository = bookingRepository;
        this.clock = clock;
    }

    private LocalDate getToday() {
        return LocalDate.now(clock);
    }

    @Transactional
    public BookingResponse createBooking(CreateBookingRequest request) {
        validateBookingDates(request.getCheckIn(), request.getCheckOut());

        Booking booking = new Booking();
        booking.setGuestName(request.getGuestName());
        booking.setPhone(request.getPhone());
        booking.setCheckIn(request.getCheckIn());
        booking.setCheckOut(request.getCheckOut());
        booking.setNumberOfGuests(request.getNumberOfGuests());
        booking.setTotalAmount(request.getTotalAmount());
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setPaymentType(request.getPaymentType() != null ? request.getPaymentType() : PaymentType.CASH);

        Booking savedBooking = bookingRepository.save(booking);
        log.info("Booking created with ID: {}", savedBooking.getId());

        return BookingResponse.fromEntity(savedBooking);
    }

    @Transactional
    public BookingResponse updateBooking(Long id, UpdateBookingRequest request) {
        Booking booking = findBookingEntityById(id);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled bookings cannot be updated");
        }

        validateBookingDates(request.getCheckIn(), request.getCheckOut());

        booking.setGuestName(request.getGuestName());
        booking.setPhone(request.getPhone());
        booking.setCheckIn(request.getCheckIn());
        booking.setCheckOut(request.getCheckOut());
        booking.setNumberOfGuests(request.getNumberOfGuests());
        booking.setTotalAmount(request.getTotalAmount());
        if (request.getPaymentType() != null) {
            booking.setPaymentType(request.getPaymentType());
        }

        Booking updatedBooking = bookingRepository.save(booking);
        log.info("Booking updated with ID: {}", updatedBooking.getId());

        return BookingResponse.fromEntity(updatedBooking);
    }

    @Transactional
    public BookingResponse cancelBooking(Long id) {
        Booking booking = findBookingEntityById(id);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is already cancelled");
        }
        if (booking.getStatus() == BookingStatus.CHECKED_OUT) {
            throw new IllegalStateException("Completed bookings cannot be cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        Booking cancelledBooking = bookingRepository.save(booking);
        log.info("Booking cancelled with ID: {}", id);

        return BookingResponse.fromEntity(cancelledBooking);
    }

    @Transactional
    public BookingResponse checkInBooking(Long id) {
        Booking booking = findBookingEntityById(id);

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Only CONFIRMED bookings can be checked in. Current status: " + booking.getStatus());
        }

        booking.setStatus(BookingStatus.CHECKED_IN);
        Booking checkedInBooking = bookingRepository.save(booking);
        log.info("Booking checked in with ID: {}", id);

        return BookingResponse.fromEntity(checkedInBooking);
    }

    @Transactional
    public BookingResponse checkOutBooking(Long id) {
        Booking booking = findBookingEntityById(id);

        if (booking.getStatus() != BookingStatus.CHECKED_IN) {
            throw new IllegalStateException("Only CHECKED_IN bookings can be checked out. Current status: " + booking.getStatus());
        }

        booking.setStatus(BookingStatus.CHECKED_OUT);
        Booking checkedOutBooking = bookingRepository.save(booking);
        log.info("Booking checked out with ID: {}", id);

        return BookingResponse.fromEntity(checkedOutBooking);
    }

    @Transactional(readOnly = true)
    public Page<BookingResponse> searchBookings(BookingSearchCriteria criteria, Pageable pageable) {
        Specification<Booking> spec = BookingSpecification.withCriteria(criteria);
        return bookingRepository.findAll(spec, pageable).map(BookingResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(BookingResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long id) {
        Booking booking = findBookingEntityById(id);
        return BookingResponse.fromEntity(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getUpcomingBookings() {
        LocalDate today = getToday();
        return bookingRepository.findByCheckInAfterAndStatusNotOrderByCheckInAsc(today, BookingStatus.CANCELLED)
                .stream()
                .map(BookingResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getCurrentBookings() {
        LocalDate today = getToday();
        return bookingRepository.findByCheckInLessThanEqualAndCheckOutGreaterThanAndStatusNot(today, today, BookingStatus.CANCELLED)
                .stream()
                .map(BookingResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getPastBookings() {
        LocalDate today = getToday();
        return bookingRepository.findByCheckOutLessThanEqualAndStatusNotOrderByCheckOutDesc(today, BookingStatus.CANCELLED)
                .stream()
                .map(BookingResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getTodayCheckIns() {
        LocalDate today = getToday();
        return bookingRepository.findByCheckInAndStatusNot(today, BookingStatus.CANCELLED)
                .stream()
                .map(BookingResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getTodayCheckOuts() {
        LocalDate today = getToday();
        return bookingRepository.findByCheckOutAndStatusNot(today, BookingStatus.CANCELLED)
                .stream()
                .map(BookingResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public Booking findBookingEntityById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
    }

    public void validateBookingDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new InvalidBookingException("Check-in and check-out dates are required");
        }
        if (!checkOut.isAfter(checkIn)) {
            throw new InvalidBookingException("Check-out date must be after check-in date");
        }
    }
}
