package com.fuelindeed.enums;

public enum BookingStatus {
	
	PENDING,        // User placed booking
    ASSIGNED,       // Station assigned delivery person
    REJECTED,       // Station rejected booking
    DELIVERED,      // Delivered successfully
    NOT_DELIVERED,
    CANCELLED, 
    APPROVED ,
    COMPLETED
}
