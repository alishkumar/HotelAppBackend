package com.hotel.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.booking.dto.CreateBookingRequest;
import com.hotel.booking.dto.BookingResponse;
import com.hotel.booking.entity.BookingStatus;
import com.hotel.booking.entity.PaymentType;
import com.hotel.booking.service.BookingService;
import com.hotel.common.exception.InvalidBookingException;
import com.hotel.common.security.JwtAuthenticationFilter;
import com.hotel.common.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /api/v1/bookings - Success returns 201 CREATED")
    void createBooking_Success_ShouldReturn201() throws Exception {
        CreateBookingRequest request = new CreateBookingRequest(
                "Anita Verma", "9876543210", "Double Room with Private Bathroom",
                LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 5),
                2, new BigDecimal("5000.00"), PaymentType.CASH
        );

        BookingResponse response = new BookingResponse(
                1092601L, "Anita Verma", "9876543210", "Double Room with Private Bathroom",
                LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 5),
                2, new BigDecimal("5000.00"), BookingStatus.CONFIRMED, PaymentType.CASH,
                LocalDateTime.now(), LocalDateTime.now()
        );

        when(bookingService.createBooking(any(CreateBookingRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/bookings")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1092601))
                .andExpect(jsonPath("$.guestName").value("Anita Verma"))
                .andExpect(jsonPath("$.roomType").value("Double Room with Private Bathroom"))
                .andExpect(jsonPath("$.status").value("CONFIRMED"))
                .andExpect(jsonPath("$.paymentType").value("CASH"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /api/v1/bookings - Bean Validation Failure returns 400 BAD REQUEST")
    void createBooking_ValidationFailure_ShouldReturn400() throws Exception {
        CreateBookingRequest invalidRequest = new CreateBookingRequest(
                "", "", "",
                null, null,
                0, new BigDecimal("-100"), null
        );

        mockMvc.perform(post("/api/v1/bookings")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /api/v1/bookings - Invalid Date Range returns 400 BAD REQUEST")
    void createBooking_InvalidDateRange_ShouldReturn400() throws Exception {
        CreateBookingRequest request = new CreateBookingRequest(
                "Anita Verma", "9876543210", "Double Room with Private Bathroom",
                LocalDate.of(2026, 9, 5), LocalDate.of(2026, 9, 1),
                2, new BigDecimal("5000.00"), PaymentType.CASH
        );

        when(bookingService.createBooking(any(CreateBookingRequest.class)))
                .thenThrow(new InvalidBookingException("Check-out date must be after check-in date"));

        mockMvc.perform(post("/api/v1/bookings")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }
}
