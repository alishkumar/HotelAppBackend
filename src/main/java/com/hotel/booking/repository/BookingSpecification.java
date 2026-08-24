package com.hotel.booking.repository;

import com.hotel.booking.dto.BookingSearchCriteria;
import com.hotel.booking.entity.Booking;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookingSpecification {

    public static Specification<Booking> createSpecification(BookingSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getGuestName() != null && !criteria.getGuestName().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("guestName")),
                        "%" + criteria.getGuestName().trim().toLowerCase() + "%"
                ));
            }

            if (criteria.getPhone() != null && !criteria.getPhone().trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        root.get("phone"), criteria.getPhone().trim()
                ));
            }

            if (criteria.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("status"), criteria.getStatus()
                ));
            }

            if (criteria.getCheckInFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("checkIn"), criteria.getCheckInFrom()
                ));
            }

            if (criteria.getCheckInTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("checkIn"), criteria.getCheckInTo()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
