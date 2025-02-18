package org.example.awensomeapp.service.impl;

import org.example.awensomeapp.dto.BookingRequestDTO;
import org.example.awensomeapp.exception.BookingUnmodifiableExcpetion;
import org.example.awensomeapp.exception.NotApprovableException;
import org.example.awensomeapp.exception.ResourceNotFoundException;
import org.example.awensomeapp.mapper.BookingMapper;
import org.example.awensomeapp.module.Booking;
import org.example.awensomeapp.module.BookingSlotEnum;
import org.example.awensomeapp.module.BookingStatusEnum;
import org.example.awensomeapp.repository.BookingRepo;
import org.example.awensomeapp.service.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

	private final BookingRepo bookingRepo;

	public BookingServiceImpl(BookingRepo bookingRepo) {
		this.bookingRepo = bookingRepo;
	}

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
		return bookingRepo.findById(id)//
				.orElseThrow(() -> new ResourceNotFoundException("booking not found with ID: " + id));
	}

	@Override
	public Booking updateBooking(Long id, BookingRequestDTO request) {
		Booking existBooking = getBookingById(id);

		chkIsAlreadyApproveOrDenie(existBooking);

		if(request.getBookingDate() != null) existBooking.setBookingDate(request.getBookingDate());
		if(request.getSlot() != null) existBooking.setSlot(request.getSlot());
		if(request.getRoomId() != null) existBooking.setRoomId(request.getRoomId());
		if(request.getNote() != null) existBooking.setNote(request.getNote());

		return bookingRepo.save(existBooking);
	}

	@Override
	public Booking approveBooking(Long id) {
		Booking booking = getBookingById(id);

		validateApproveBooking(booking);

		booking.setStatus(BookingStatusEnum.APPROVE);
		return bookingRepo.save(booking);
	}

	@Override
	public Booking denieBooking(Long id) {
		Booking booking = getBookingById(id);
		booking.setStatus(BookingStatusEnum.DENIE);
		return bookingRepo.save(booking);
	}

	@Override
	public void deleteBooking(Long id) {
		getBookingById(id);
		bookingRepo.deleteById(id);
	}

	private void validateApproveBooking(Booking booking) {
		chkIsAlreadyApproveOrDenie(booking);
		chkExistApprovedBooking(booking);
	}

	private void chkIsAlreadyApproveOrDenie(Booking booking) {
		if (booking.getStatus() != BookingStatusEnum.CREATE) {
			throw new BookingUnmodifiableExcpetion();
		}
	}

	private void chkExistApprovedBooking(Booking booking) {

		Long roomId = booking.getRoomId();
		BookingSlotEnum slot = booking.getSlot();
		LocalDate bookingDate = booking.getBookingDate();

		if (bookingRepo.existApprovedBooking(roomId, bookingDate, slot)) {
			throw new NotApprovableException();
		}
	}
}
