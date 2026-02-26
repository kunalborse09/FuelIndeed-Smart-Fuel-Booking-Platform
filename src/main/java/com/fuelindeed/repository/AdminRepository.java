package com.fuelindeed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fuelindeed.entity.Admin;
import com.fuelindeed.enums.StationStatus;

public interface AdminRepository extends JpaRepository<Admin, Long>{
 
	Optional<Admin> findByEmail(String email);
	
	
}
