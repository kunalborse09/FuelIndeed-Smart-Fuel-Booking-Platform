package com.fuelindeed.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fuelindeed.entity.Booking;
import com.fuelindeed.entity.FuelStation;
import com.fuelindeed.enums.BookingStatus;
import com.fuelindeed.enums.StationStatus;


public interface FuelStationRepository extends JpaRepository<FuelStation, Long> {

	Optional<FuelStation> findByEmail(String email);

    List<FuelStation> findByCityAndStatus(String city, StationStatus status);

    List<FuelStation> findByPincodeAndStatus(String pincode, StationStatus status);
    
    List<FuelStation> findByStatus(StationStatus status);
    
    List<FuelStation> findByAreaOrCityOrPincode(String area, String city, String pincode);
    
    FuelStation findByEmailAndPassword(String email, String password);
    
    long countByStatus(StationStatus status);   // ✅ ADD THIS
    
    

    
    @Query("SELECT f FROM FuelStation f WHERE " +
    	       "f.status = :status AND " +
    	       "(LOWER(f.area) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
    	       "OR LOWER(f.city) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    	List<FuelStation> searchByKeyword(
    	        @Param("keyword") String keyword,
    	        @Param("status") StationStatus status
    	);

} 
