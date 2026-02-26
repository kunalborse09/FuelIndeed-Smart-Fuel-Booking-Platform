package com.fuelindeed.controller.web;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fuelindeed.entity.Admin;
import com.fuelindeed.entity.FuelStation;
import com.fuelindeed.enums.StationStatus;
import com.fuelindeed.repository.AdminRepository;
import com.fuelindeed.repository.FuelStationRepository;
import com.fuelindeed.repository.UserRepository;
import com.fuelindeed.service.AdminService;
import com.fuelindeed.service.FuelStationService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final FuelStationRepository fuelStationRepository;
	private final FuelStationService fuelStationService;
	private final UserRepository userRepository;
	
	private final AdminService adminService;
	


	public AdminController(FuelStationRepository fuelStationRepository, FuelStationService fuelStationService,
			UserRepository userRepository,  PasswordEncoder passwordEncoder, AdminService adminService, AdminRepository adminRepository) {

		this.fuelStationRepository = fuelStationRepository;
		this.fuelStationService = fuelStationService;
		this.userRepository = userRepository;
		
		this.adminService = adminService;
	}

	// ========================= Login =====================

	@GetMapping("/login")
	public String adminLogin() {
		return "admin-login";
	}

	// 🔹 REGISTER PAGE
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("admin", new Admin());
		return "admin-register";
	}

	
	
	
	
	
	
	
	
	
	// 🔹 REGISTER PROCESS
	@PostMapping("/register")
	public String register(@ModelAttribute Admin admin) {

		adminService.register(admin); // 🔥 Service handles password & role
		return "redirect:/admin/login";

	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {

	    model.addAttribute("pendingCount",
	            fuelStationRepository.countByStatus(StationStatus.PENDING));

	    model.addAttribute("approvedCount",
	            fuelStationRepository.countByStatus(StationStatus.APPROVED));

	    model.addAttribute("rejectedCount",
	            fuelStationRepository.countByStatus(StationStatus.REJECTED));

	    model.addAttribute("userCount",
	            userRepository.count());

	    return "admin-dashboard";
	}

	// 🔹 Pending Stations
	@GetMapping("/stations/pending")
	public String pendingStations(Model model) {
		model.addAttribute("stations", fuelStationRepository.findByStatus(StationStatus.PENDING));
		return "admin-pending-stations";
	}

	// ================= VIEW APPROVED =================

	@GetMapping("/stations/approved")
	public String approvedStations(Model model) {

		model.addAttribute("stations", fuelStationRepository.findByStatus(StationStatus.APPROVED));
		return "admin-approved-stations";
	}

	// ================= VIEW REJECTED =================

	@GetMapping("/stations/rejected")
	public String rejectedStations(Model model) {

		model.addAttribute("stations", fuelStationRepository.findByStatus(StationStatus.REJECTED));
		return "admin-rejected-stations";
	}

	// ================= VIEW USERS =================

	@GetMapping("/users")
	public String users(Model model) {

		model.addAttribute("users", userRepository.findAll());
		return "admin-users";
	}

	// ================= APPROVE =================

	@GetMapping("/station/approve/{id}")
	public String approve(@PathVariable Long id) {
		fuelStationService.approveStation(id);
		return "redirect:/admin/stations/pending";
	}

	// ================= REJECT =================

	@GetMapping("/station/reject/{id}")
	public String reject(@PathVariable Long id) {
		fuelStationService.rejectStation(id);
		return "redirect:/admin/stations/pending";
	}

	// ================= LOGOUT =================

}
