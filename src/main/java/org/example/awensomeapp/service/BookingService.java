package org.example.awensomeapp.service;

import org.example.awensomeapp.dto.BookingRequestDTO;
import org.example.awensomeapp.module.Booking;

import java.util.List;

public interface BookingService {

	Long createBooking (BookingRequestDTO request);
	List<Booking> getAllBooking ();
	Booking getBookingById (Long id);
	Booking updateBooking (Long id, BookingRequestDTO request);
	Booking approveBooking (Long id);
	Booking denieBooking (Long id);
	void deleteBooking(Long id);

}
