package org.example.awensomeapp.dto;

import lombok.Data;
import org.example.awensomeapp.module.BookingSlotEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
public class BookingRequestDTO {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate bookingDate;

	private BookingSlotEnum slot;
	private Long roomId;
	private String note;
}
