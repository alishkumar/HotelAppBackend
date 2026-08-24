package com.hotel.booking.dto;

import com.hotel.booking.entity.BookingStatus;
import java.time.LocalDate;

public class BookingSearchCriteria {

    private String guestName;
    private String phone;
    private BookingStatus status;
    private LocalDate checkInFrom;
    private LocalDate checkInTo;

    public BookingSearchCriteria() {
    }

    public BookingSearchCriteria(String guestName, String phone, BookingStatus status,
                                 LocalDate checkInFrom, LocalDate checkInTo) {
        this.guestName = guestName;
        this.phone = phone;
        this.status = status;
        this.checkInFrom = checkInFrom;
        this.checkInTo = checkInTo;
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
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
