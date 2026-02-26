package com.fuelindeed.controller.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fuelindeed.entity.Booking;
import com.fuelindeed.entity.FuelStation;
import com.fuelindeed.entity.User;
import com.fuelindeed.enums.BookingStatus;
import com.fuelindeed.enums.StationStatus;
import com.fuelindeed.repository.BookingRepository;
import com.fuelindeed.repository.FuelStationRepository;
import com.fuelindeed.repository.UserRepository;
import com.fuelindeed.service.BookingService;
import com.fuelindeed.service.FuelStationService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserDashboardController {

    private BookingService bookingService;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final FuelStationRepository fuelStationRepository;

    public UserDashboardController(BookingService bookingService, UserRepository userRepository, BookingRepository bookingRepository,  FuelStationRepository fuelStationRepository ) { 
		super();
		this.bookingService = bookingService;
		this.userRepository = userRepository;
		this.bookingRepository = bookingRepository;
		this.fuelStationRepository = fuelStationRepository;
	}

    @GetMapping("/dashboard")
    public String userDashboard(Model model) {

        List<FuelStation> stations = fuelStationRepository.findByStatus(StationStatus.APPROVED);

        if (stations == null) {
            stations = new ArrayList<>(); // Null-safe
        }

        model.addAttribute("stations", stations);

        return "user-dashboard";
    }

    
    @GetMapping("/bookings/{status}")
    public String bookingsByStatusAjax(
            @PathVariable BookingStatus status,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Booking> bookings = bookingRepository.findByUserAndStatus(user, status);

        model.addAttribute("bookings", bookings);
        return "user-bookings"; // content-only fragment for AJAX
    }



}
