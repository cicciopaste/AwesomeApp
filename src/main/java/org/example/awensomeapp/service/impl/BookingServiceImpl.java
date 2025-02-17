package org.example.awensomeapp.service.impl;

import org.example.awensomeapp.dto.BookingRequestDTO;
import org.example.awensomeapp.mapper.BookingMapper;
import org.example.awensomeapp.module.Booking;
import org.example.awensomeapp.module.BookingStatusEnum;
import org.example.awensomeapp.repository.BookingRepo;
import org.example.awensomeapp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepo bookingRepo;

	@Override
	public Long createBooking(BookingRequestDTO request) {
		Booking booking = BookingMapper.mapBookingRequestToEntity(request);
		return bookingRepo.save(booking).getId();
	}

	@Override
	public List<Booking> getAllBooking() {
		return bookingRepo.findAll();
	}

	@Override
	public Booking getBookingById(Long id) {
		return bookingRepo.findById(id).orElse(new Booking());
	}

	@Override
	public Booking updateBooking(Long id, BookingStatusEnum status) {
		return null;
	}

	@Override
	public void deleteBooking(Long id) {
		bookingRepo.deleteById(id);
	}
}
