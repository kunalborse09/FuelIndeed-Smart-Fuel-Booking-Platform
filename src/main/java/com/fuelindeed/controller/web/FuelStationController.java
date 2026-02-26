package com.fuelindeed.controller.web;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fuelindeed.entity.FuelStation;
import com.fuelindeed.enums.BookingStatus;
import com.fuelindeed.enums.StationStatus;
import com.fuelindeed.repository.BookingRepository;
import com.fuelindeed.repository.FuelStationRepository;
import com.fuelindeed.service.BookingService;
import com.fuelindeed.service.FuelStationService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/station")
public class FuelStationController {

    private final FuelStationService fuelStationService;

    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    
    private final FuelStationRepository fuelStationRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    public FuelStationController(FuelStationService fuelStationService, FuelStationRepository fuelStationRepository,BookingRepository bookingRepository, PasswordEncoder passwordEncoder, BookingService bookingService) {
        this.fuelStationService = fuelStationService;
        this.fuelStationRepository = fuelStationRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }
    
    // ========================== Login ========================
    @GetMapping("/login")
    public String stationLogin() {
        return "station-login";
    }

    
    
    
    

    

 // ================= REGISTER =================
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("station", new FuelStation());
        return "station-register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute FuelStation station) {

        station.setPassword(passwordEncoder.encode(station.getPassword()));
        station.setRole("ROLE_STATION");     // 🔥 IMPORTANT
        station.setStatus(StationStatus.PENDING);

        fuelStationRepository.save(station);

        return "redirect:/station/login";
    }
    
    
    

   
    // ------------------------forget paasward -----------------------------//
    
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "station-forgot-password";
    }
    
    
    
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword,
                                Model model) {

        Optional<FuelStation> optionalStation = fuelStationRepository.findByEmail(email);

        if (optionalStation.isPresent()) {

            FuelStation station = optionalStation.get();

            // 🔐 Always encode password
            station.setPassword(passwordEncoder.encode(newPassword));

            fuelStationRepository.save(station);

            model.addAttribute("message", "Password updated successfully");
            return "redirect:/station/login";
        }

        model.addAttribute("error", "Email not found");
        return "station-forgot-password";
    }

    
    
    
    
    @GetMapping("/dashboard")
    public String dashboard(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            Model model) {

        FuelStation station = fuelStationRepository
                .findByEmail(userDetails.getUsername())
                .orElseThrow();

        Long stationId = station.getId();

        model.addAttribute("pendingCount",
                bookingService.countByStationAndStatus(stationId, BookingStatus.PENDING));
        model.addAttribute("assignedCount",
                bookingService.countByStationAndStatus(stationId, BookingStatus.ASSIGNED));
        model.addAttribute("deliveredCount",
                bookingService.countByStationAndStatus(stationId, BookingStatus.DELIVERED));
        model.addAttribute("rejectedCount",
                bookingService.countByStationAndStatus(stationId, BookingStatus.REJECTED));
        
        
        model.addAttribute("station", station);

        return "station-dashboard";
    }
    
    // ================= LOGOUT =================

   
    @GetMapping("/fuel")
    public String fuelForm(Authentication authentication, Model model) {

        String email = authentication.getName();

        FuelStation station =
                fuelStationRepository.findByEmail(email)
                        .orElse(null);

        model.addAttribute("station", station);

        return "station-fuel";
    }



    
    
    
    
    
    
    @GetMapping("/complete-profile")
    public String completeProfile(Authentication authentication,
                                  Model model) {

        String email = authentication.getName();

        FuelStation station =
                fuelStationRepository.findByEmail(email)
                        .orElse(null);

        model.addAttribute("station", station);

        return "station-complete-profile";
    }



    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute FuelStation updatedStation,
                                Authentication authentication) {

        String email = authentication.getName();

        FuelStation station =
                fuelStationRepository.findByEmail(email)
                        .orElse(null);

        station.setName(updatedStation.getName());
        station.setOpenTime(updatedStation.getOpenTime());
        station.setCloseTime(updatedStation.getCloseTime());
        station.setAddress(updatedStation.getAddress());
        station.setArea(updatedStation.getArea());
        station.setCity(updatedStation.getCity());
        station.setPincode(updatedStation.getPincode());
        station.setPetrolQty(updatedStation.getPetrolQty());
        station.setPetrolRate(updatedStation.getPetrolRate());
        station.setDieselQty(updatedStation.getDieselQty());
        station.setDieselRate(updatedStation.getDieselRate());
        station.setContact(updatedStation.getContact());

        fuelStationRepository.save(station);

        return "redirect:/station/dashboard";
    }

    
    

    @PostMapping("/fuel")
    public String updateFuel(@ModelAttribute FuelStation formStation,
                             Authentication authentication) {

        // 1️⃣ Get logged-in station email
        String email = authentication.getName();

        // 2️⃣ Fetch station from DB
        FuelStation station = fuelStationRepository
                .findByEmail(email)
                .orElse(null);

        if (station == null) {
            return "redirect:/login";
        }

        // 3️⃣ Update only fuel-related fields
        station.setPetrolQty(formStation.getPetrolQty());
        station.setPetrolRate(formStation.getPetrolRate());
        station.setDieselQty(formStation.getDieselQty());
        station.setDieselRate(formStation.getDieselRate());

        station.setPetrolAvailable(formStation.isPetrolAvailable());
        station.setDieselAvailable(formStation.isDieselAvailable());

        // 4️⃣ Update last modified time
        station.setLastUpdated(java.time.LocalDateTime.now());

        // 5️⃣ Save
        fuelStationRepository.save(station);

        return "redirect:/station/dashboard";
    }
}
