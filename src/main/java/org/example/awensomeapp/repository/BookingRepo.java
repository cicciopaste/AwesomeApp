package org.example.awensomeapp.repository;

import org.example.awensomeapp.module.Booking;
import org.example.awensomeapp.module.BookingSlotEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

	@Query("SELECT COUNT(u) > 0 FROM Booking u WHERE u.roomId = :roomId and u.bookingDate = :date and u.slot = :slot and u.status = APPROVE")
	boolean existApprovedBooking (@Param("room") Long roomId, @Param("date") Date bookingDate, @Param("slot") String slot);

}
