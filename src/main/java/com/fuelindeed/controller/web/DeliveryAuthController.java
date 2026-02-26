package com.fuelindeed.controller.web;

import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fuelindeed.entity.DeliveryPerson;
import com.fuelindeed.entity.FuelStation;
import com.fuelindeed.enums.StationStatus;
import com.fuelindeed.repository.DeliveryPersonRepository;
import com.fuelindeed.repository.FuelStationRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/delivery")
public class DeliveryAuthController {

	 private final DeliveryPersonRepository repo;
	    private final PasswordEncoder passwordEncoder;
	    private final DeliveryPersonRepository deliveryPersonRepository;

	    private final FuelStationRepository fuelStationRepository;
	    public DeliveryAuthController(DeliveryPersonRepository repo,
	                                  PasswordEncoder passwordEncoder, FuelStationRepository fuelStationRepository, DeliveryPersonRepository deliveryPersonRepository) {
	        this.repo = repo;
	        this.passwordEncoder = passwordEncoder;;
	        this.fuelStationRepository = fuelStationRepository;
	        this.deliveryPersonRepository = deliveryPersonRepository;
	    }

	    
	    // ====================== Login ============================
	    
	    @GetMapping("/login")
	    public String deliveryLogin() {
	        return "delivery-login";
	    }

	    
	    
 
	 // ================= REGISTER =================
	    @GetMapping("/register")
	    public String openRegister(Model model) {
	    	 model.addAttribute("stations",
	    	            fuelStationRepository.findByStatus(StationStatus.APPROVED));

	        return "delivery-register";
	    }


	 // ================= REGISTER SAVE =================
	    @PostMapping("/register")
	    public String registerDeliveryPerson(
	            @RequestParam String name,
	            @RequestParam String email,
	            @RequestParam String password,
	            @RequestParam Long stationId
	    ) {

	        FuelStation station = fuelStationRepository
	                .findById(stationId)
	                .orElseThrow(() -> new RuntimeException("Station not found"));

	        DeliveryPerson dp = new DeliveryPerson();
	        dp.setName(name);
	        dp.setEmail(email);
	        dp.setPassword(passwordEncoder.encode(password));
	        dp.setRole("ROLE_DELIVERY");
	        dp.setStation(station);

	        deliveryPersonRepository.save(dp);

	        return "redirect:/delivery/login";
	    }

	 
	    
	    
	    
	    
	 // ================= RESET PASSWORD =================
	    @GetMapping("/forgot-password")
	    public String forgotPasswordPage() {
	        return "delivery-forgot-password";
	    }

	    @PostMapping("/reset-password")
	    public String resetPassword(@RequestParam String email,
	                                @RequestParam String newPassword,
	                                Model model) {

	        DeliveryPerson dp = repo.findByEmail(email)
	                .orElse(null);

	        if (dp == null) {
	            model.addAttribute("error", "Email not found");
	            return "delivery-forgot-password";
	        }

	        dp.setPassword(passwordEncoder.encode(newPassword));
	        repo.save(dp);

	        return "redirect:/delivery/login";
	    }
}
