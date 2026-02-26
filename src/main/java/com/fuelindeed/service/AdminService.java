package com.fuelindeed.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fuelindeed.entity.Admin;
import com.fuelindeed.repository.AdminRepository;


@Service
public class AdminService {

	private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository,
                        PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(Admin admin) {

        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // 🔐 Encode password
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        // 🔥 Set role here
        admin.setRole("ROLE_ADMIN");

        adminRepository.save(admin);
    }
}
