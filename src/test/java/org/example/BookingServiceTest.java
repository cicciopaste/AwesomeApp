package org.example;

import org.example.awensomeapp.dto.BookingRequestDTO;
import org.example.awensomeapp.exception.BookingUnmodifiableExcpetion;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

	@Mock
	private BookingRepo bookingRepo;

	@InjectMocks
	private BookingServiceImpl bookingService;

	private Booking sampleBooking;
	private Booking sampleApproveBooking;


	@BeforeEach
	void setUp() {
		sampleBooking = new Booking();
		sampleBooking.setId(1L);
		sampleBooking.setBookingDate(null);
		sampleBooking.setStatus(BookingStatusEnum.CREATE);
		sampleBooking.setRoomId(101L);
		sampleBooking.setSlot(BookingSlotEnum.MORNING);

		sampleApproveBooking = new Booking();
		sampleApproveBooking.setId(2L);
		sampleApproveBooking.setBookingDate(null);
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
		requestDTO.setBookingDate(null);
		requestDTO.setSlot(BookingSlotEnum.AFTERNOON);
		requestDTO.setRoomId(102L);

		Booking newBooking = new Booking();
		newBooking.setId(2L);
		newBooking.setBookingDate(null);
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
		requestDTO.setBookingDate(null);
		requestDTO.setSlot(BookingSlotEnum.AFTERNOON);
		requestDTO.setRoomId(103L);

		Booking updatedBooking = new Booking();
		updatedBooking.setId(1L);
		updatedBooking.setBookingDate(null);
		updatedBooking.setStatus(BookingStatusEnum.CREATE);
		updatedBooking.setRoomId(103L);
		updatedBooking.setSlot(BookingSlotEnum.AFTERNOON);

		// Mock repository behavior
		when(bookingRepo.findById(1L)).thenReturn(Optional.of(sampleBooking));
		when(bookingRepo.save(sampleBooking)).thenReturn(updatedBooking);

		// Call service method
		Booking result = bookingService.updateBooking(1L, requestDTO);

		// Verify the update
		assertNotNull(result);
		assertEquals(103L, result.getRoomId());
		assertEquals(BookingSlotEnum.AFTERNOON, result.getSlot());
	}

	@Test
	void testUpdateBooking_UnmodifiableExcpetion() {
		BookingRequestDTO requestDTO = new BookingRequestDTO();
		requestDTO.setBookingDate(null);
		requestDTO.setSlot(BookingSlotEnum.AFTERNOON);
		requestDTO.setRoomId(103L);

		// Mock repository behavior
		when(bookingRepo.findById(1L)).thenReturn(Optional.of(sampleApproveBooking));

		// Verify the repository save method was not called
		verify(bookingRepo, times(0)).save(any(Booking.class));

		// Assert exception is thrown
		Exception exception = assertThrows(BookingUnmodifiableExcpetion.class, () -> {
			bookingService.updateBooking(1L, requestDTO);
		});
	}

	@Test
	void testDeleteBooking_Success() {
		// Mock repository behavior
		when(bookingRepo.findById(1L)).thenReturn(Optional.of(sampleBooking));
		doNothing().when(bookingRepo).deleteById(sampleBooking.getId());

		// Call service method
		assertDoesNotThrow(() -> bookingService.deleteBooking(1L));

		// Verify the repository delete method was called
		verify(bookingRepo, times(1)).deleteById(sampleBooking.getId());
	}
}
