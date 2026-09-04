package com.hotel.booking.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BookingSource {
    BOOKING_COM("Booking.com"),
    MAKEMYTRIP("MakeMyTrip"),
    GOOGLE("Google"),
    DIRECT("Direct"),
    AGODA("Agoda"),
    OTHER("Other");

    private final String displayName;

    BookingSource(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static BookingSource fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        for (BookingSource source : BookingSource.values()) {
            if (source.name().equalsIgnoreCase(value.trim()) ||
                source.displayName.equalsIgnoreCase(value.trim()) ||
                source.name().replace("_", "").equalsIgnoreCase(value.replaceAll("[^a-zA-Z0-9]", ""))) {
                return source;
            }
        }
        return OTHER;
    }
}
