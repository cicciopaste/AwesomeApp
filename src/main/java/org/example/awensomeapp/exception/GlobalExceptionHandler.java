package org.example.awensomeapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final String NOT_APPROVABLE_MESSAGE = "booking unprovable slot is busy";
	private static final String NOT_MODIFIABLE_MESSAGE = "booking request is approved or denie create new one";

	@ExceptionHandler(BookingUnmodifiableExcpetion.class)
	@ResponseStatus(HttpStatus.NOT_MODIFIED)
	public ResponseEntity<String> handleBookingUnmodifiableExcpetion() {
		return new ResponseEntity<>(NOT_MODIFIABLE_MESSAGE,HttpStatus.NOT_MODIFIED);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleResourceNotFoundException(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotApprovableException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<String> handleNotApprovableException() {
		return new ResponseEntity<>(NOT_APPROVABLE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
