package org.example.awensomeapp.controller;

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
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@GetMapping("/{id}")
	ResponseEntity<Booking> readBooking (@PathVariable("id") Long id){
		return ResponseEntity.ok(bookingService.getBookingById(id));
	}

	@PostMapping()
	ResponseEntity<Long>  createBooking (@RequestBody BookingRequestDTO request){
		return ResponseEntity.ok(bookingService.createBooking(request));
	}

	@DeleteMapping("/{id}")
	ResponseEntity<String> deleteBooking (@PathVariable("id") Long id){
		bookingService.deleteBooking(id);
		return ResponseEntity.ok("Eliminazione Avvenuta con successo");
	}

	@PutMapping("/{id}")
	ResponseEntity<Booking> updateBooking (@PathVariable("id") Long id, @RequestBody BookingRequestDTO request){
		return ResponseEntity.ok(bookingService.updateBooking(id, request));
	}

	@PutMapping("/{id}/approve")
	ResponseEntity<Booking> approveBooking (@PathVariable("id") Long id){
		return ResponseEntity.ok(bookingService.approveBooking(id));
	}

	@PutMapping("/{id}/denie")
	ResponseEntity<Booking> denieBooking (@PathVariable("id") Long id){
		return ResponseEntity.ok(bookingService.denieBooking(id));
	}

	@GetMapping("/queue")
	ResponseEntity<List<Booking>> readQueueBooking (){
		return ResponseEntity.ok(bookingService.getAllBooking());
	}
}
