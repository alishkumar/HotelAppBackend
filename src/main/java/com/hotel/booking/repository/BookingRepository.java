package com.hotel.booking.repository;

import com.hotel.booking.entity.Booking;
import com.hotel.booking.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    @Query("SELECT b FROM Booking b WHERE b.checkIn > :today AND b.status != :status ORDER BY b.checkIn ASC")
    List<Booking> findByCheckInAfterAndStatusNotOrderByCheckInAsc(@Param("today") LocalDate today, @Param("status") BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.checkIn <= :checkInMax AND b.checkOut > :checkOutMin AND b.status != :status")
    List<Booking> findByCheckInLessThanEqualAndCheckOutGreaterThanAndStatusNot(@Param("checkInMax") LocalDate checkInMax, @Param("checkOutMin") LocalDate checkOutMin, @Param("status") BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.checkOut <= :today AND b.status != :status ORDER BY b.checkOut DESC")
    List<Booking> findByCheckOutLessThanEqualAndStatusNotOrderByCheckOutDesc(@Param("today") LocalDate today, @Param("status") BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.checkIn = :today AND b.status != :status")
    List<Booking> findByCheckInAndStatusNot(@Param("today") LocalDate today, @Param("status") BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.checkOut = :today AND b.status != :status")
    List<Booking> findByCheckOutAndStatusNot(@Param("today") LocalDate today, @Param("status") BookingStatus status);

    @Query("SELECT MAX(b.id) FROM Booking b WHERE b.id >= :minId AND b.id <= :maxId")
    Long findMaxIdInRange(@Param("minId") Long minId, @Param("maxId") Long maxId);
}
