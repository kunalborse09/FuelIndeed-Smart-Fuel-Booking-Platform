package com.fuelindeed.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fuelindeed.entity.Booking;
import com.fuelindeed.entity.FuelStation;
import com.fuelindeed.entity.User;
import com.fuelindeed.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long>{

	 List<Booking> findByStationIdAndStatus(Long stationId, BookingStatus status);

	    List<Booking> findByStationIdAndBookingDate(Long stationId, LocalDate date);

	    List<Booking> findByUserId(Long userId);
	    
	    List<Booking> findByUserAndStatus(User user, BookingStatus status);
	    
	    List<Booking> findByStationAndStatus(FuelStation station, String string);
	    
	    List<Booking> findByDeliveryPersonIdAndStatus(Long deliveryPersonId, BookingStatus status); 
	    
	    long countByUserId(Long userId);

	    long countByUserIdAndStatus(Long userId, BookingStatus status);
	    
	    List<Booking> findByStatus(BookingStatus status);
	    
	    long countByDeliveryPerson_IdAndStatus(Long deliveryPersonId, BookingStatus status);

	    List<Booking> findByDeliveryPerson_IdAndStatus(Long id, BookingStatus status);
	    
	    long countByStationIdAndStatus(Long stationId, BookingStatus status);

	   
	    List<Booking> findTop5ByOrderByCreatedAtDesc();
	   
	   

	    // ✅ ADD THIS (Recent Activity)
	    List<Booking> findTop5ByDeliveryPerson_IdOrderByCreatedAtDesc(Long deliveryPersonId);
	   
	    List<Booking> findTop5ByStation_IdOrderByCreatedAtDesc(Long stationId);

	    
	    
	    
	    
	    List<Booking> findByUser(User user);

	   

	    long countByUser(User user);

	    long countByUserAndStatus(User user, BookingStatus status);

	    List<Booking> findTop5ByUserOrderByCreatedAtDesc(User user);
 
}
