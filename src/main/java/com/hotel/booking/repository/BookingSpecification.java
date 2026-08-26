package com.hotel.booking.repository;

import com.hotel.booking.dto.BookingSearchCriteria;
import com.hotel.booking.entity.Booking;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BookingSpecification {

    public static Specification<Booking> withCriteria(BookingSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria == null) {
                return cb.conjunction();
            }

            if (StringUtils.hasText(criteria.getGuestName())) {
                predicates.add(cb.like(cb.lower(root.get("guestName")),
                        "%" + criteria.getGuestName().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(criteria.getPhone())) {
                predicates.add(cb.equal(root.get("phone"), criteria.getPhone()));
            }

            if (criteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }

            if (criteria.getPaymentType() != null) {
                predicates.add(cb.equal(root.get("paymentType"), criteria.getPaymentType()));
            }

            if (criteria.getCheckInFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("checkIn"), criteria.getCheckInFrom()));
            }

            if (criteria.getCheckInTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("checkIn"), criteria.getCheckInTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
