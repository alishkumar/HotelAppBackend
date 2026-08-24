package com.hotel.booking.controller;

import com.hotel.booking.dto.CreateBookingRequest;
import com.hotel.booking.dto.UpdateBookingRequest;
import com.hotel.booking.dto.BookingResponse;
import com.hotel.booking.dto.BookingSearchCriteria;
import com.hotel.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody CreateBookingRequest request) {
        BookingResponse createdBooking = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @GetMapping
    public ResponseEntity<Page<BookingResponse>> searchBookings(
            @ModelAttribute BookingSearchCriteria criteria,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<BookingResponse> bookings = bookingService.searchBookings(criteria, pageable);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        BookingResponse booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<BookingResponse>> getUpcomingBookings() {
        List<BookingResponse> bookings = bookingService.getUpcomingBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/current")
    public ResponseEntity<List<BookingResponse>> getCurrentBookings() {
        List<BookingResponse> bookings = bookingService.getCurrentBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/past")
    public ResponseEntity<List<BookingResponse>> getPastBookings() {
        List<BookingResponse> bookings = bookingService.getPastBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/today/check-ins")
    public ResponseEntity<List<BookingResponse>> getTodayCheckIns() {
        List<BookingResponse> bookings = bookingService.getTodayCheckIns();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/today/check-outs")
    public ResponseEntity<List<BookingResponse>> getTodayCheckOuts() {
        List<BookingResponse> bookings = bookingService.getTodayCheckOuts();
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateBookingRequest request) {
        BookingResponse updatedBooking = bookingService.updateBooking(id, request);
        return ResponseEntity.ok(updatedBooking);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long id) {
        BookingResponse cancelledBooking = bookingService.cancelBooking(id);
        return ResponseEntity.ok(cancelledBooking);
    }

    @PatchMapping("/{id}/check-in")
    public ResponseEntity<BookingResponse> checkInBooking(@PathVariable Long id) {
        BookingResponse checkedInBooking = bookingService.checkInBooking(id);
        return ResponseEntity.ok(checkedInBooking);
    }

    @PatchMapping("/{id}/check-out")
    public ResponseEntity<BookingResponse> checkOutBooking(@PathVariable Long id) {
        BookingResponse checkedOutBooking = bookingService.checkOutBooking(id);
        return ResponseEntity.ok(checkedOutBooking);
    }
}
