package com.hotel.booking.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BookingSourceConverter implements AttributeConverter<BookingSource, String> {

    @Override
    public String convertToDatabaseColumn(BookingSource attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDisplayName();
    }

    @Override
    public BookingSource convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }
        return BookingSource.fromString(dbData);
    }
}
