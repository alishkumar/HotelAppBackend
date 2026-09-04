package com.hotel.booking.repository;

import com.hotel.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    @Query("SELECT b FROM Booking b WHERE b.checkIn > :today ORDER BY b.checkIn ASC")
    List<Booking> findByCheckInAfterOrderByCheckInAsc(@Param("today") LocalDate today);

    @Query("SELECT b FROM Booking b WHERE b.checkIn <= :checkInMax AND b.checkOut > :checkOutMin ORDER BY b.checkIn ASC")
    List<Booking> findByCheckInLessThanEqualAndCheckOutGreaterThan(@Param("checkInMax") LocalDate checkInMax, @Param("checkOutMin") LocalDate checkOutMin);

    @Query("SELECT b FROM Booking b WHERE b.checkOut <= :today ORDER BY b.checkOut DESC")
    List<Booking> findByCheckOutLessThanEqualOrderByCheckOutDesc(@Param("today") LocalDate today);

    @Query("SELECT b FROM Booking b WHERE b.checkIn = :today ORDER BY b.id ASC")
    List<Booking> findByCheckIn(@Param("today") LocalDate today);

    @Query("SELECT b FROM Booking b WHERE b.checkOut = :today ORDER BY b.id ASC")
    List<Booking> findByCheckOut(@Param("today") LocalDate today);

    @Query("SELECT MAX(b.id) FROM Booking b WHERE b.id >= :minId AND b.id <= :maxId")
    Long findMaxIdInRange(@Param("minId") Long minId, @Param("maxId") Long maxId);
}
