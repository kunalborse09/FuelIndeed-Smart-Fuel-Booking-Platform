package com.fuelindeed.controller.web;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fuelindeed.config.CustomUserDetailsService;
import com.fuelindeed.entity.Booking;
import com.fuelindeed.entity.DeliveryPerson;
import com.fuelindeed.enums.BookingStatus;
import com.fuelindeed.repository.BookingRepository;
import com.fuelindeed.repository.DeliveryPersonRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/delivery")
public class DeliveryBookingController {

	private final BookingRepository bookingRepository;
	private final DeliveryPersonRepository deliveryRepository;

    public DeliveryBookingController(BookingRepository bookingRepository,DeliveryPersonRepository deliveryRepository) {
        this.bookingRepository = bookingRepository;
        this.deliveryRepository = deliveryRepository;
    }

    // ==================================================
    // 1️⃣ VIEW ASSIGNED BOOKINGS
    // ==================================================
    @GetMapping("/assigned")
    public String viewAssignedBookings(Authentication authentication,
                                       Model model) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/delivery/login";
        }

        String email = authentication.getName(); // logged in email

        // fetch delivery person from DB using email
        DeliveryPerson dp = deliveryRepository
                .findByEmail(email)
                .orElse(null);

        if (dp == null) {
            return "redirect:/delivery/login";
        }

        model.addAttribute("bookings",
                bookingRepository.findByDeliveryPersonIdAndStatus(
                        dp.getId(),
                        BookingStatus.ASSIGNED));

        return "delivery-assigned";
    }


    // ==================================================
    // 2️⃣ MARK BOOKING AS DELIVERED
    // ==================================================
    @PostMapping("/mark-delivered")
    public String markDelivered(@RequestParam Long bookingId,
                                @RequestParam String code,
                                Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/delivery/login";
        }

        String email = authentication.getName();

        DeliveryPerson dp = deliveryRepository
                .findByEmail(email)
                .orElse(null);

        if (dp == null) {
            return "redirect:/delivery/login";
        }

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getDeliveryPerson().getId().equals(dp.getId())) {
            throw new RuntimeException("Unauthorized delivery access");
        }

        if (!booking.getDeliveryCode().equals(code)) {
            throw new RuntimeException("Invalid Delivery Code");
        }

        booking.setStatus(BookingStatus.DELIVERED);
        bookingRepository.save(booking);

        return "redirect:/delivery/delivered";
    } 
     
    
    
 // ================= VIEW NOT DELIVERED BOOKINGS =================
    @GetMapping("/not-delivered")
    public String viewNotDelivered(Authentication authentication, Model model) {

        String email = authentication.getName();

        DeliveryPerson dp = deliveryRepository
                .findByEmail(email)
                .orElse(null);

        if (dp == null) {
            return "redirect:/delivery/login";
        }

        model.addAttribute("bookings",
                bookingRepository.findByDeliveryPersonIdAndStatus(
                        dp.getId(),
                        BookingStatus.NOT_DELIVERED));

        return "delivery-not-delivered";
    }
    
    
    
    
    @PostMapping("/mark-not-delivered")
    public String markNotDelivered(@RequestParam Long bookingId,
                                   Authentication authentication) {

        String email = authentication.getName();

        DeliveryPerson dp = deliveryRepository
                .findByEmail(email)
                .orElse(null);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow();

        if (!booking.getDeliveryPerson().getId().equals(dp.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        booking.setStatus(BookingStatus.NOT_DELIVERED);
        bookingRepository.save(booking);

        return "redirect:/delivery/not-delivered";
    }
    
    
    
    

 // ================= DELIVERY DASHBOARD =================
    @GetMapping("/dashboard")
    public String deliveryDashboard(Authentication authentication, Model model) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/delivery/login";
        }

        String email = authentication.getName();

        DeliveryPerson dp = deliveryRepository
                .findByEmail(email)
                .orElse(null);

        if (dp == null) {
            return "redirect:/delivery/login";
        }

        // ===== COUNTS =====
        Long assignedCount = bookingRepository
                .countByDeliveryPerson_IdAndStatus(
                        dp.getId(),
                        BookingStatus.ASSIGNED);

        Long deliveredCount = bookingRepository
                .countByDeliveryPerson_IdAndStatus(
                        dp.getId(),
                        BookingStatus.DELIVERED);

        Long notDeliveredCount = bookingRepository
                .countByDeliveryPerson_IdAndStatus(
                        dp.getId(),
                        BookingStatus.NOT_DELIVERED);

        // ===== RECENT ACTIVITY (NEW) =====
        List<Booking> recentBookings =
                bookingRepository
                        .findTop5ByDeliveryPerson_IdOrderByCreatedAtDesc(dp.getId());

        // ===== MODEL =====
        model.addAttribute("assignedCount", assignedCount);
        model.addAttribute("deliveredCount", deliveredCount);
        model.addAttribute("notDeliveredCount", notDeliveredCount);
        model.addAttribute("recentBookings", recentBookings);

        return "delivery-dashboard";
    }
    
    @GetMapping("/delivered")
    public String viewDelivered(Authentication authentication, Model model) {

        String email = authentication.getName();

        DeliveryPerson dp = deliveryRepository.findByEmail(email).orElse(null);

        model.addAttribute("bookings",
                bookingRepository.findByDeliveryPerson_IdAndStatus(
                        dp.getId(),
                        BookingStatus.DELIVERED));

        return "delivery-delivered";
    }
}
