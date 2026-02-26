package com.fuelindeed.controller.web;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fuelindeed.entity.Booking;
import com.fuelindeed.entity.DeliveryPerson;
import com.fuelindeed.entity.FuelStation;
import com.fuelindeed.enums.BookingStatus;
import com.fuelindeed.repository.BookingRepository;
import com.fuelindeed.repository.DeliveryPersonRepository;
import com.fuelindeed.repository.FuelStationRepository;
import com.fuelindeed.service.BookingService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/station/bookings")
public class FuelStationBookingController {

	 private final BookingService bookingService;
	    private final FuelStationRepository stationRepo;
	    private final DeliveryPersonRepository deliveryRepo;

	    public FuelStationBookingController(BookingService bookingService,
	                                        FuelStationRepository stationRepo,
	                                        DeliveryPersonRepository deliveryRepo) {
	        this.bookingService = bookingService;
	        this.stationRepo = stationRepo;
	        this.deliveryRepo = deliveryRepo;
	    }

	    private FuelStation getStation(String email) {
	        return stationRepo.findByEmail(email).orElseThrow();
	    }

	    // ================= PENDING =================
	    @GetMapping("/pending")
	    public String pending(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
	                          Model model) {

	        FuelStation station = getStation(userDetails.getUsername());

	        model.addAttribute("bookings",
	                bookingService.findByStationAndStatus(station.getId(), BookingStatus.PENDING));

	        model.addAttribute("deliveryPersons",
	        		deliveryRepo.findByStation_Id(station.getId()));


	        return "station-pending";
	    }

	    // APPROVE (ASSIGN DELIVERY)
	    @PostMapping("/approve")
	    public String approve(@RequestParam Long bookingId,
	                          @RequestParam Long deliveryPersonId) {

	        bookingService.assignDelivery(bookingId, deliveryPersonId);
	        return "redirect:/station/bookings/assigned";
	    }

	    // REJECT
	    @PostMapping("/reject")
	    public String reject(@RequestParam Long bookingId) {

	        bookingService.rejectBooking(bookingId);
	        return "redirect:/station/bookings/rejected";
	    }

	    // ================= ASSIGNED =================
	    @GetMapping("/assigned")
	    public String assigned(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
	                           Model model) {

	        FuelStation station = getStation(userDetails.getUsername());

	        model.addAttribute("bookings",
	                bookingService.findByStationAndStatus(station.getId(), BookingStatus.ASSIGNED));

	        return "station-assigned";
	    }

	    // ================= DELIVERED =================
	    @GetMapping("/delivered")
	    public String delivered(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
	                            Model model) {

	        FuelStation station = getStation(userDetails.getUsername());

	        model.addAttribute("bookings",
	                bookingService.findByStationAndStatus(station.getId(), BookingStatus.DELIVERED));

	        return "station-delivered";
	    }

	    // ================= REJECTED =================
	    @GetMapping("/rejected")
	    public String rejected(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
	                           Model model) {

	        FuelStation station = getStation(userDetails.getUsername());

	        model.addAttribute("bookings",
	                bookingService.findByStationAndStatus(station.getId(), BookingStatus.REJECTED));

	        return "station-rejected";
	    }
}
