package org.example.awensomeapp.mapper;

import org.example.awensomeapp.dto.BookingRequestDTO;
import org.example.awensomeapp.module.Booking;

public class BookingMapper {

	public static Booking mapBookingRequestToEntity (BookingRequestDTO request){
		return new Booking();
	}
}
