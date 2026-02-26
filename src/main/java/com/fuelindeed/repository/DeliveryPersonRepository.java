package com.fuelindeed.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fuelindeed.entity.DeliveryPerson;
import com.fuelindeed.entity.FuelStation;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long>{ 
	
	Optional<DeliveryPerson> findByEmail(String email);

    List<DeliveryPerson> findByStationId(Long stationId);
    
    DeliveryPerson findByEmailAndPassword(String email, String password);
    List<DeliveryPerson> findByStation_Id(Long stationId);


   


}
