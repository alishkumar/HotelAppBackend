package com.hotel.booking.dto;

import com.hotel.booking.entity.BookingStatus;
import com.hotel.booking.entity.PaymentType;
import java.time.LocalDate;

public class BookingSearchCriteria {

    private String guestName;
    private String phone;
    private String roomType;
    private BookingStatus status;
    private PaymentType paymentType;
    private LocalDate checkInFrom;
    private LocalDate checkInTo;

    public BookingSearchCriteria() {
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

    public LocalDate getCheckInFrom() {
        return checkInFrom;
    }

    public void setCheckInFrom(LocalDate checkInFrom) {
        this.checkInFrom = checkInFrom;
    }

    public LocalDate getCheckInTo() {
        return checkInTo;
    }

    public void setCheckInTo(LocalDate checkInTo) {
        this.checkInTo = checkInTo;
    }
}
