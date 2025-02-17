package org.example.awensomeapp.controller;

import org.example.awensomeapp.dto.BookingRequestDTO;
import org.example.awensomeapp.module.BookingStatusEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@GetMapping("/{id}")
	ResponseEntity<String> readBooking (@PathVariable("id") Long bookingCode){
		 return ResponseEntity.ok("");
	}

	@PostMapping()
	void createBooking (@RequestBody BookingRequestDTO request){
	}

	@DeleteMapping("/{id}")
	ResponseEntity<String> deleteBooking (@PathVariable("id") Long bookingCode){
		return ResponseEntity.ok("");
	}

	@PutMapping("/{id}/status/{status}")
	ResponseEntity<String> updateBooking (@PathVariable("id") Long bookingCode, @PathVariable("status") BookingStatusEnum status){
		return ResponseEntity.ok("");
	}

	@GetMapping("/queue")
	ResponseEntity<String> readQueueBooking (){
		return ResponseEntity.ok("");
	}
}
