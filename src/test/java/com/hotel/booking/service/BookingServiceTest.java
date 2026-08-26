package com.hotel.booking.service;

import com.hotel.booking.dto.CreateBookingRequest;
import com.hotel.booking.dto.UpdateBookingRequest;
import com.hotel.booking.dto.BookingResponse;
import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.BookingStatus;
import com.hotel.booking.entity.PaymentType;
import com.hotel.booking.repository.BookingRepository;
import com.hotel.common.exception.InvalidBookingException;
import com.hotel.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    private BookingRepository bookingRepository;
    private Clock clock;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingRepository = mock(BookingRepository.class);
        clock = Clock.fixed(Instant.parse("2026-08-24T10:00:00Z"), ZoneId.of("Asia/Kolkata"));
        bookingService = new BookingService(bookingRepository, clock);
    }

    @Test
    @DisplayName("Create booking successfully when dates are valid")
    void createBooking_Success() {
        CreateBookingRequest request = new CreateBookingRequest(
                "Rahul Kumar", "9876543210", "Family Room",
                LocalDate.of(2026, 8, 25), LocalDate.of(2026, 8, 27),
                2, new BigDecimal("3500.00"), new BigDecimal("1000.00"), PaymentType.CASH
        );

        when(bookingRepository.save(any(Booking.class))).thenAnswer(i -> i.getArgument(0));

        BookingResponse response = bookingService.createBooking(request);

        assertNotNull(response);
        assertEquals(25082601L, response.getId());
        assertEquals("Rahul Kumar", response.getGuestName());
        assertEquals("Family Room", response.getRoomType());
        assertEquals(new BigDecimal("1000.00"), response.getAdvanceAmount());
        assertEquals(BookingStatus.CONFIRMED, response.getStatus());
        assertEquals(PaymentType.CASH, response.getPaymentType());

        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    @DisplayName("Create booking fails when check-out is before check-in")
    void createBooking_InvalidDates_ShouldThrowException() {
        CreateBookingRequest request = new CreateBookingRequest(
                "Rahul Kumar", "9876543210", "Family Room",
                LocalDate.of(2026, 8, 27), LocalDate.of(2026, 8, 25),
                2, new BigDecimal("3500.00"), new BigDecimal("1000.00"), PaymentType.CASH
        );

        assertThrows(InvalidBookingException.class, () -> bookingService.createBooking(request));
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    @DisplayName("Update booking successfully")
    void updateBooking_Success() {
        Booking existingBooking = new Booking(
                25082601L, "Rahul Kumar", "9876543210", "Family Room",
                LocalDate.of(2026, 8, 25), LocalDate.of(2026, 8, 27),
                2, new BigDecimal("3500.00"), new BigDecimal("1000.00"),
                BookingStatus.CONFIRMED, PaymentType.CASH
        );

        UpdateBookingRequest updateRequest = new UpdateBookingRequest(
                "Rahul Sharma", "9876543210", "Deluxe Room",
                LocalDate.of(2026, 8, 26), LocalDate.of(2026, 8, 28),
                3, new BigDecimal("4500.00"), new BigDecimal("1500.00"), PaymentType.CASH
        );

        when(bookingRepository.findById(25082601L)).thenReturn(Optional.of(existingBooking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(i -> i.getArgument(0));

        BookingResponse response = bookingService.updateBooking(25082601L, updateRequest);

        assertNotNull(response);
        assertEquals("Rahul Sharma", response.getGuestName());
        assertEquals("Deluxe Room", response.getRoomType());
        assertEquals(new BigDecimal("1500.00"), response.getAdvanceAmount());
        assertEquals(3, response.getNumberOfGuests());
        assertEquals(PaymentType.CASH, response.getPaymentType());
    }

    @Test
    @DisplayName("Cancel booking successfully changes status to CANCELLED")
    void cancelBooking_Success() {
        Booking existingBooking = new Booking(
                25082601L, "Rahul Kumar", "9876543210", "Family Room",
                LocalDate.of(2026, 8, 25), LocalDate.of(2026, 8, 27),
                2, new BigDecimal("3500.00"), null, BookingStatus.CONFIRMED, PaymentType.CASH
        );

        when(bookingRepository.findById(25082601L)).thenReturn(Optional.of(existingBooking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(i -> i.getArgument(0));

        BookingResponse response = bookingService.cancelBooking(25082601L);

        assertEquals(BookingStatus.CANCELLED, response.getStatus());
    }

    @Test
    @DisplayName("Get booking by ID throws ResourceNotFoundException if ID not found")
    void getBookingById_NotFound_ShouldThrowException() {
        when(bookingRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookingService.getBookingById(99L));
    }
}
