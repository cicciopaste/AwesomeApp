package org.example;

import org.example.awensomeapp.dto.BookingRequestDTO;
import org.example.awensomeapp.exception.BookingUnmodifiableExcpetion;
import org.example.awensomeapp.exception.NotApprovableException;
import org.example.awensomeapp.exception.ResourceNotFoundException;
import org.example.awensomeapp.module.Booking;
import org.example.awensomeapp.module.BookingSlotEnum;
import org.example.awensomeapp.module.BookingStatusEnum;
import org.example.awensomeapp.repository.BookingRepo;
import org.example.awensomeapp.service.BookingService;
import org.example.awensomeapp.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

	@Mock
	private BookingRepo bookingRepo;

	@InjectMocks
	private BookingServiceImpl bookingService;

	private Booking sampleBooking;
	private Booking sampleApproveBooking;

	private LocalDate dataCreazione = LocalDate.of(2025,2,18);


	@BeforeEach
	void setUp() {
		sampleBooking = new Booking();
		sampleBooking.setId(1L);
		sampleBooking.setBookingDate(dataCreazione);
		sampleBooking.setStatus(BookingStatusEnum.CREATE);
		sampleBooking.setRoomId(101L);
		sampleBooking.setSlot(BookingSlotEnum.MORNING);

		sampleApproveBooking = new Booking();
		sampleApproveBooking.setId(1L);
		sampleApproveBooking.setBookingDate(dataCreazione);
		sampleApproveBooking.setStatus(BookingStatusEnum.APPROVE);
		sampleApproveBooking.setRoomId(103L);
		sampleApproveBooking.setSlot(BookingSlotEnum.AFTERNOON);
	}

	@Test
	void testGetBookingById_Success() {
		// Mock repository behavior
		when(bookingRepo.findById(1L)).thenReturn(Optional.of(sampleBooking));

		// Call service method
		Booking result = bookingService.getBookingById(1L);

		// Verify the result
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals(101L, result.getRoomId());
	}

	@Test
	void testGetBookingById_NotFound() {
		// Mock repository to return empty result
		when(bookingRepo.findById(1L)).thenReturn(Optional.empty());

		// Assert exception is thrown
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			bookingService.getBookingById(1L);
		});

		assertEquals("booking not found with ID: 1", exception.getMessage());
	}

	@Test
	void testCreateBooking_Success() {
		BookingRequestDTO requestDTO = new BookingRequestDTO();
		requestDTO.setBookingDate(dataCreazione);
		requestDTO.setSlot(BookingSlotEnum.AFTERNOON);
		requestDTO.setRoomId(102L);

		Booking newBooking = new Booking();
		newBooking.setId(2L);
		newBooking.setBookingDate(dataCreazione);
		newBooking.setStatus(BookingStatusEnum.CREATE);
		newBooking.setRoomId(102L);
		newBooking.setSlot(BookingSlotEnum.AFTERNOON);

		// Mock repository save behavior
		when(bookingRepo.save(any(Booking.class))).thenReturn(newBooking);

		// Call service method
		Long id = bookingService.createBooking(requestDTO);

		// Verify the result
		assertEquals(2L, id);
	}

	@Test
	void testUpdateBooking_Success() {
		BookingRequestDTO requestDTO = new BookingRequestDTO();
		requestDTO.setBookingDate(dataCreazione);
		requestDTO.setSlot(BookingSlotEnum.AFTERNOON);
		requestDTO.setRoomId(103L);

		Booking updatedBooking = new Booking();
		updatedBooking.setId(1L);
		updatedBooking.setBookingDate(dataCreazione);
		updatedBooking.setStatus(BookingStatusEnum.CREATE);
		updatedBooking.setRoomId(103L);
		updatedBooking.setSlot(BookingSlotEnum.AFTERNOON);

		// Mock repository behavior
		when(bookingRepo.findById(sampleBooking.getId())).thenReturn(Optional.of(sampleBooking));
		when(bookingRepo.save(sampleBooking)).thenReturn(updatedBooking);

		// Call service method
		Booking result = bookingService.updateBooking(sampleBooking.getId(), requestDTO);

		// Verify the update
		assertNotNull(result);
		assertEquals(103L, result.getRoomId());
		assertEquals(BookingSlotEnum.AFTERNOON, result.getSlot());
	}

	@Test
	void testUpdateBooking_UnmodifiableExcpetion() {
		BookingRequestDTO requestDTO = new BookingRequestDTO();
		requestDTO.setBookingDate(dataCreazione);
		requestDTO.setSlot(BookingSlotEnum.AFTERNOON);
		requestDTO.setRoomId(103L);

		// Mock repository behavior
		when(bookingRepo.findById(sampleApproveBooking.getId())).thenReturn(Optional.of(sampleApproveBooking));

		// Assert exception is thrown
		assertThrows(BookingUnmodifiableExcpetion.class, () -> {
			bookingService.updateBooking(1L, requestDTO);
		});

		// Verify the repository save method was not called
		verify(bookingRepo, times(0)).save(any(Booking.class));
	}

	@Test
	void testApproveBooking_Success() {
		Booking approvedBooking = new Booking();
		approvedBooking.setId(1L);
		approvedBooking.setBookingDate(dataCreazione);
		approvedBooking.setStatus(BookingStatusEnum.APPROVE);
		approvedBooking.setRoomId(103L);
		approvedBooking.setSlot(BookingSlotEnum.AFTERNOON);

		// Mock repository behavior
		when(bookingRepo.findById(sampleBooking.getId())).thenReturn(Optional.of(sampleBooking));
		when(bookingRepo.save(sampleBooking)).thenReturn(approvedBooking);

		// Call service method
		Booking result = bookingService.approveBooking(sampleBooking.getId());

		// Verify the repository save method was not called
		verify(bookingRepo, times(1)).save(any(Booking.class));

		// Verify approve
		assertEquals(BookingStatusEnum.APPROVE, result.getStatus());
	}

	@Test
	void testApproveBooking_SlotBussy() {
		// Mock repository behavior
		when(bookingRepo.findById(sampleBooking.getId())).thenReturn(Optional.of(sampleBooking));
		when(bookingRepo.existApprovedBooking(sampleBooking.getRoomId(), sampleBooking.getBookingDate(), sampleBooking.getSlot())).thenReturn(true);

		// Call service method
		assertThrows(NotApprovableException.class, () -> {
			bookingService.approveBooking(1L);
		});

		// Verify the repository save method was not called
		verify(bookingRepo, times(0)).save(any(Booking.class));

	}

	@Test
	void testDeleteBooking_Success() {
		// Mock repository behavior
		when(bookingRepo.findById(1L)).thenReturn(Optional.of(sampleBooking));
		doNothing().when(bookingRepo).deleteById(sampleBooking.getId());

		// Call service method
		assertDoesNotThrow(() -> bookingService.deleteBooking(sampleBooking.getId()));

		// Verify the repository delete method was called
		verify(bookingRepo, times(1)).deleteById(sampleBooking.getId());
	}

	@Test
	void testDeleteBooking_NotFound() {
		// Mock repository behavior
		when(bookingRepo.findById(1L)).thenReturn(Optional.empty());

		// Call service method
		assertThrows(ResourceNotFoundException.class, () -> {
			bookingService.deleteBooking(1L);
		});

		// Verify the repository delete method was called
		verify(bookingRepo, times(0)).deleteById(sampleBooking.getId());
	}
}
