package com.hotel.booking.dto;

import com.hotel.booking.entity.BookingSource;
import com.hotel.booking.entity.PaymentType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateBookingRequest {

    @NotBlank(message = "Guest name is required")
    private String guestName;

    @NotBlank(message = "Phone number is required")
    private String phone;

    private String roomType;

    private BookingSource source;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkIn;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOut;

    @NotNull(message = "Number of guests is required")
    @Min(value = 1, message = "Number of guests must be at least 1")
    private Integer numberOfGuests;

    @NotNull(message = "Total amount is required")
    @PositiveOrZero(message = "Total amount must be greater than or equal to 0")
    private BigDecimal totalAmount;

    @PositiveOrZero(message = "Advance amount must be greater than or equal to 0")
    private BigDecimal advanceAmount;

    private PaymentType paymentType;

    public CreateBookingRequest() {
    }

    public CreateBookingRequest(String guestName, String phone, String roomType,
                                LocalDate checkIn, LocalDate checkOut,
                                Integer numberOfGuests, BigDecimal totalAmount,
                                PaymentType paymentType) {
        this(guestName, phone, roomType, null, checkIn, checkOut, numberOfGuests, totalAmount, null, paymentType);
    }

    public CreateBookingRequest(String guestName, String phone, String roomType,
                                LocalDate checkIn, LocalDate checkOut,
                                Integer numberOfGuests, BigDecimal totalAmount,
                                BigDecimal advanceAmount, PaymentType paymentType) {
        this(guestName, phone, roomType, null, checkIn, checkOut, numberOfGuests, totalAmount, advanceAmount, paymentType);
    }

    public CreateBookingRequest(String guestName, String phone, String roomType, BookingSource source,
                                LocalDate checkIn, LocalDate checkOut,
                                Integer numberOfGuests, BigDecimal totalAmount,
                                BigDecimal advanceAmount, PaymentType paymentType) {
        this.guestName = guestName;
        this.phone = phone;
        this.roomType = roomType;
        this.source = source;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfGuests = numberOfGuests;
        this.totalAmount = totalAmount;
        this.advanceAmount = advanceAmount;
        this.paymentType = paymentType != null ? paymentType : PaymentType.CASH;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public BookingSource getSource() {
        return source;
    }

    public void setSource(BookingSource source) {
        this.source = source;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(BigDecimal advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
