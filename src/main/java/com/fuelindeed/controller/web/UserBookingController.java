package com.fuelindeed.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fuelindeed.entity.Booking;
import com.fuelindeed.entity.FuelStation;
import com.fuelindeed.entity.User;
import com.fuelindeed.enums.BookingStatus;
import com.fuelindeed.repository.BookingRepository;
import com.fuelindeed.repository.FuelStationRepository;
import com.fuelindeed.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserBookingController {

	@Autowired
    private FuelStationRepository fuelStationRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    // ==================================================
    // 1️⃣ OPEN BOOKING PAGE
    // ==================================================
    @GetMapping("/book/{stationId}")
    public String openBookingForm(@PathVariable Long stationId,
                                  Model model,
                                  Authentication authentication) {

        if (authentication == null) {
            return "redirect:/user/login";
        }

        FuelStation station = fuelStationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        model.addAttribute("station", station);
        return "user-book-fuel";
    }

    // ==================================================
    // 2️⃣ SAVE BOOKING
    // ==================================================
    @PostMapping("/save-booking")
    public String saveBooking(@RequestParam Long stationId,
                              @RequestParam String fuelType,
                              @RequestParam double quantity,
                              @RequestParam String deliveryAddress,
                              @RequestParam(required = false) Double latitude,
                              @RequestParam(required = false) Double longitude,
                              Authentication authentication) {

        if (authentication == null) {
            return "redirect:/user/login";
        }

        // ✅ Logged-in user
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FuelStation station = fuelStationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        double availableQty;
        double rate;

        if ("PETROL".equalsIgnoreCase(fuelType)) {
            availableQty = station.getPetrolQty();
            rate = station.getPetrolRate();
        } else {
            availableQty = station.getDieselQty();
            rate = station.getDieselRate();
        }

        if (quantity > availableQty) {
            throw new RuntimeException("Requested quantity not available");
        }

        double totalBill = rate * quantity;

        // 🔐 Delivery OTP
        String deliveryCode = String.valueOf((int) (Math.random() * 9000) + 1000);

        Booking booking = new Booking();
        booking.setFuelType(fuelType);
        booking.setQuantity(quantity);
        booking.setTotalBill(totalBill);
        booking.setDeliveryCode(deliveryCode);
        booking.setStatus(BookingStatus.PENDING);

        // ✅ NEW DATA
        booking.setDeliveryAddress(deliveryAddress);
        booking.setLatitude(latitude);
        booking.setLongitude(longitude);

        booking.setUser(user);
        booking.setStation(station);

        bookingRepository.save(booking);

        return "redirect:/user/dashboard";
    }
    
}
