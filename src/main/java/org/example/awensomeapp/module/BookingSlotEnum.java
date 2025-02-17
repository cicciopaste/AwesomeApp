package org.example.awensomeapp.module;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BookingSlotEnum {
	MORNING("MRN"),
	AFTERNOON("AFT"),
	NIGTH("NGT");

	public final String value;

	BookingSlotEnum(String value) {
		this.value = value;
	}
}
