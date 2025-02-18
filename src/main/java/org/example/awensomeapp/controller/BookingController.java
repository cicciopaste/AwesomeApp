package org.example.awensomeapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.awensomeapp.dto.BookingRequestDTO;
import org.example.awensomeapp.module.Booking;
import org.example.awensomeapp.module.BookingStatusEnum;
import org.example.awensomeapp.repository.BookingRepo;
import org.example.awensomeapp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/booking")
@Tag(name = "Booking Management", description = "APIs for managing Awesome Music Booking")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@GetMapping("/{id}")
	@Operation(summary = "Read a booking data")
	ResponseEntity<Booking> readBooking (@PathVariable("id") Long id){
		return ResponseEntity.ok(bookingService.getBookingById(id));
	}

	@PostMapping()
	@Operation(summary = "Create a new booking", description = "Saves a new booking to the database")
	ResponseEntity<Long>  createBooking (@RequestBody BookingRequestDTO request){
		return ResponseEntity.ok(bookingService.createBooking(request));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a booking")
	ResponseEntity<String> deleteBooking (@PathVariable("id") Long id){
		bookingService.deleteBooking(id);
		return ResponseEntity.ok("Eliminazione Avvenuta con successo");
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a booking", description = "updatable data are Slot, Room and BookingDate")
	ResponseEntity<Booking> updateBooking (@PathVariable("id") Long id, @RequestBody BookingRequestDTO request){
		return ResponseEntity.ok(bookingService.updateBooking(id, request));
	}

	@PutMapping("/{id}/approve")
	@Operation(summary = "approve a booking", description = "it is possible only if the booking not already approved or denie and the slot is not busy")
	ResponseEntity<Booking> approveBooking (@PathVariable("id") Long id){
		return ResponseEntity.ok(bookingService.approveBooking(id));
	}

	@PutMapping("/{id}/denie")
	@Operation(summary = "denie a booking", description = "it is possible only if the booking not already approved or denie")
	ResponseEntity<Booking> denieBooking (@PathVariable("id") Long id){
		return ResponseEntity.ok(bookingService.denieBooking(id));
	}

	@GetMapping("/queue")
	@Operation(summary = "get all bookings")
	ResponseEntity<List<Booking>> readQueueBooking (){
		return ResponseEntity.ok(bookingService.getAllBooking());
	}
}
