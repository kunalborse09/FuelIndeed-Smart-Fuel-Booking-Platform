package com.fuelindeed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fuelindeed.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	 Optional<User> findByEmail(String email);

	 
	
}
