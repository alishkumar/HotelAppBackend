package com.hotel.booking.dto;

import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.BookingSource;
import com.hotel.booking.entity.BookingStatus;
import com.hotel.booking.entity.PaymentType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingResponse {

    private Long id;
    private String guestName;
    private String phone;
    private String roomType;
    private BookingSource source;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer numberOfGuests;
    private BigDecimal totalAmount;
    private BigDecimal advanceAmount;
    private BookingStatus status;
    private PaymentType paymentType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BookingResponse() {
    }

    public BookingResponse(Long id, String guestName, String phone, String roomType, BookingSource source,
                           LocalDate checkIn, LocalDate checkOut, Integer numberOfGuests,
                           BigDecimal totalAmount, BigDecimal advanceAmount,
                           BookingStatus status, PaymentType paymentType,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.guestName = guestName;
        this.phone = phone;
        this.roomType = roomType;
        this.source = source;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfGuests = numberOfGuests;
        this.totalAmount = totalAmount;
        this.advanceAmount = advanceAmount;
        this.status = status;
        this.paymentType = paymentType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static BookingResponse fromEntity(Booking booking) {
        if (booking == null) {
            return null;
        }
        return new BookingResponse(
                booking.getId(),
                booking.getGuestName(),
                booking.getPhone(),
                booking.getRoomType(),
                booking.getSource(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                booking.getNumberOfGuests(),
                booking.getTotalAmount(),
                booking.getAdvanceAmount(),
                booking.getStatus(),
                booking.getPaymentType() != null ? booking.getPaymentType() : PaymentType.CASH,
                booking.getCreatedAt(),
                booking.getUpdatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
