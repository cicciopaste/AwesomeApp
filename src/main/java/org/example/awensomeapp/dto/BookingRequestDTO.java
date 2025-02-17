package org.example.awensomeapp.dto;

import lombok.Data;
import org.example.awensomeapp.module.BookingSlotEnum;

import java.util.Date;

@Data
public class BookingRequestDTO {
	private Date bookingDate;
	private BookingSlotEnum slot;
	private Long roomId;
	private String note;
}
