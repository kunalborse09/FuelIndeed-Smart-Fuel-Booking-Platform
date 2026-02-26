package com.fuelindeed.config;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fuelindeed.entity.Admin;
import com.fuelindeed.entity.DeliveryPerson;
import com.fuelindeed.entity.FuelStation;
import com.fuelindeed.entity.User;
import com.fuelindeed.repository.AdminRepository;
import com.fuelindeed.repository.DeliveryPersonRepository;
import com.fuelindeed.repository.FuelStationRepository;
import com.fuelindeed.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	
	private final AdminRepository adminRepo;
    private final UserRepository userRepo;
    private final FuelStationRepository stationRepo;
    private final DeliveryPersonRepository deliveryRepo;

    public CustomUserDetailsService(AdminRepository adminRepo,
                                    UserRepository userRepo,
                                    FuelStationRepository stationRepo,
                                    DeliveryPersonRepository deliveryRepo) {
        this.adminRepo = adminRepo;
        this.userRepo = userRepo;
        this.stationRepo = stationRepo;
        this.deliveryRepo = deliveryRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // ADMIN
        Admin admin = adminRepo.findByEmail(email).orElse(null);
        if (admin != null) {
            return buildUser(admin.getEmail(), admin.getPassword(), admin.getRole());
        }

        // USER
        User user = userRepo.findByEmail(email).orElse(null);
        if (user != null) {
            return buildUser(user.getEmail(), user.getPassword(), user.getRole());
        }

        // STATION
        FuelStation station = stationRepo.findByEmail(email).orElse(null);
        if (station != null) {

            // Optional: only allow approved station login 
            if (station.getStatus().name().equals("PENDING")) {
                throw new UsernameNotFoundException("Station not approved yet");
            }

            return buildUser(station.getEmail(), station.getPassword(), station.getRole());
        }

        // DELIVERY
        DeliveryPerson dp = deliveryRepo.findByEmail(email).orElse(null);
        if (dp != null) {
            return buildUser(dp.getEmail(), dp.getPassword(), dp.getRole());
        }

        throw new UsernameNotFoundException("User not found");
    }

    private UserDetails buildUser(String email,
                                  String password,
                                  String role) {

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(email)
                .password(password)
                .authorities(Collections.singleton(
                        new SimpleGrantedAuthority(role)))
                .build();
    }

	
}
