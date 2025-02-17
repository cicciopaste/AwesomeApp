package org.example.awensomeapp.module;

public enum BookingStatusEnum {
	APPROVE("A"),
	DENIE("D"),
	CREATE("C");

	public final String value;

	BookingStatusEnum(String value) {
		this.value = value;
	}
}
