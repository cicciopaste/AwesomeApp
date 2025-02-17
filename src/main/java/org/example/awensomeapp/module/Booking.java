package org.example.awensomeapp.module;

import lombok.Data;

import java.util.Date;

@Data
public class Booking {
	private Long id;
	private Date bookingDate;
	private BookingSlotEnum slot;
	private Long roomId;
	private String note;
}
