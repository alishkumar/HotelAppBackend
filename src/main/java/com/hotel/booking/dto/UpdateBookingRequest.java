package com.hotel.booking.dto;

import com.hotel.booking.entity.PaymentType;
import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateBookingRequest {

    private String guestName;
    private String phone;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer numberOfGuests;
    private BigDecimal totalAmount;
    private PaymentType paymentType;

    public UpdateBookingRequest() {
    }

    public UpdateBookingRequest(String guestName, String phone,
                                LocalDate checkIn, LocalDate checkOut,
                                Integer numberOfGuests, BigDecimal totalAmount) {
        this(guestName, phone, checkIn, checkOut, numberOfGuests, totalAmount, null);
    }

    public UpdateBookingRequest(String guestName, String phone,
                                LocalDate checkIn, LocalDate checkOut,
                                Integer numberOfGuests, BigDecimal totalAmount,
                                PaymentType paymentType) {
        this.guestName = guestName;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfGuests = numberOfGuests;
        this.totalAmount = totalAmount;
        this.paymentType = paymentType;
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

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
