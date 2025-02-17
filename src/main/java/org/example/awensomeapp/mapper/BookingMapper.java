package org.example.awensomeapp.mapper;

import org.example.awensomeapp.dto.BookingRequestDTO;
import org.example.awensomeapp.module.Booking;
import org.example.awensomeapp.module.BookingStatusEnum;

public class BookingMapper {

	public static Booking mapBookingRequestToEntity (BookingRequestDTO request){
		Booking booking = new Booking();
		booking.setBookingDate(request.getBookingDate());
		booking.setNote(request.getNote());
		booking.setSlot(request.getSlot());
		booking.setRoomId(request.getRoomId());

		return booking;
	}
}
