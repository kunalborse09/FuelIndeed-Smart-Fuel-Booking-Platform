package com.fuelindeed.controller.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fuelindeed.entity.Booking;
import com.fuelindeed.entity.FuelStation;
import com.fuelindeed.entity.User;
import com.fuelindeed.enums.BookingStatus;
import com.fuelindeed.enums.StationStatus;
import com.fuelindeed.repository.BookingRepository;
import com.fuelindeed.repository.FuelStationRepository;
import com.fuelindeed.repository.UserRepository;
import com.fuelindeed.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final BookingRepository bookingRepository;
	private final FuelStationRepository fuelStationRepository;
	private final UserService userService;


	public UserController(UserRepository userRepository,
            PasswordEncoder passwordEncoder, FuelStationRepository fuelStationRepository, UserService userService, BookingRepository bookingRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.bookingRepository = bookingRepository;
		this.fuelStationRepository = fuelStationRepository;
		this.userService = userService;
	}
	
	//--------------------------- Login ------------------------------
	
	@GetMapping("/login")
	public String userLogin() {
	    return "user-login";
	} 

    

	// ================= REGISTER =================
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "user-register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");   // 🔥 IMPORTANT

        userRepository.save(user);

        return "redirect:/user/login";
    }

    // ================= FORGOT PASSWORD =================
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "user-forgot-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword,
                                Model model) {

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            model.addAttribute("error", "Email not found");
            return "user-forgot-password";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "redirect:/user/login";
    }


   

    
    @GetMapping("/search")
    public String searchPage() {
        return "user-search-station";
    }

    @PostMapping("/search")
    public String searchStation(@RequestParam String area, Model model) {

        if (area == null || area.trim().isEmpty()) {
            model.addAttribute("error", "Please enter city or area");
            return "user-dashboard";
        }

        List<FuelStation> stations =
                fuelStationRepository.searchByKeyword(
                        area.trim(),
                        StationStatus.APPROVED);

        if (stations.isEmpty()) {
            model.addAttribute("error", "No station found");
        }

        model.addAttribute("stations", stations);

        return "user-dashboard";
    }

 
    
    
    

    
    
    
   
}
