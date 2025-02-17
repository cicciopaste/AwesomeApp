package org.example.awensomeapp.service.impl;

import org.example.awensomeapp.dto.BookingRequestDTO;
import org.example.awensomeapp.module.Booking;
import org.example.awensomeapp.module.BookingStatusEnum;
import org.example.awensomeapp.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
	@Override
	public Long createBooking(BookingRequestDTO request) {
		return null;
	}

	@Override
	public List<Booking> getAllBooking() {
		return null;
	}

	@Override
	public Booking getBookingById(Long id) {
		return null;
	}

	@Override
	public Booking updateBooking(Long id, BookingStatusEnum status) {
		return null;
	}

	@Override
	public void deleteBooking(Long id) {

	}
}
