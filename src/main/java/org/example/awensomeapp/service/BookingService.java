package org.example.awensomeapp.service;

import org.example.awensomeapp.dto.BookingRequestDTO;
import org.example.awensomeapp.module.Booking;
import org.example.awensomeapp.module.BookingStatusEnum;

import java.util.List;

public interface BookingService {

	Long createBooking (BookingRequestDTO request);
	List<Booking> getAllBooking ();
	Booking getBookingById (Long id);
	Booking updateBooking (Long id, BookingStatusEnum status);
	void deleteBooking(Long id);

}
