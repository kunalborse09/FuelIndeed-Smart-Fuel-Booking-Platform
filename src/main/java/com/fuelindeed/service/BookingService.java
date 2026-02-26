package com.fuelindeed.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fuelindeed.entity.Booking;
import com.fuelindeed.entity.DeliveryPerson;
import com.fuelindeed.entity.FuelStation;
import com.fuelindeed.entity.User;
import com.fuelindeed.enums.BookingStatus;
import com.fuelindeed.repository.BookingRepository;
import com.fuelindeed.repository.DeliveryPersonRepository;
@Service
public class BookingService {

	private final BookingRepository bookingRepository;

	private final DeliveryPersonRepository deliveryPersonRepository;
    public BookingService(BookingRepository bookingRepository, DeliveryPersonRepository deliveryPersonRepository) {
        this.bookingRepository = bookingRepository;
        this.deliveryPersonRepository = deliveryPersonRepository;
    }

    public Booking createBooking(User user, FuelStation station,
                                 String fuelType, double qty) {

        double rate;
        if (fuelType.equalsIgnoreCase("PETROL")) {
            rate = station.getPetrolRate();
        } else {
            rate = station.getDieselRate();
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setStation(station);
        booking.setFuelType(fuelType);
        booking.setQuantity(qty);
        booking.setTotalBill(rate * qty);
        booking.setStatus(BookingStatus.PENDING);
        booking.setBookingDate(LocalDate.now());
        booking.setCreatedAt(LocalDateTime.now());

        return bookingRepository.save(booking);
    }
    
    
   
    public long countByUser(Long userId) {
        return bookingRepository.countByUserId(userId);
    }

   
    public long countByUserAndStatus(Long userId, BookingStatus status) {
        return bookingRepository.countByUserIdAndStatus(userId, status);
 
    }

    
    
    public long countByStationAndStatus(Long stationId, BookingStatus status) {
        return bookingRepository.countByStationIdAndStatus(stationId, status);
    }

    public List<Booking> findByStationAndStatus(Long stationId, BookingStatus status) {
        return bookingRepository.findByStationIdAndStatus(stationId, status);
    }

    public void assignDelivery(Long bookingId, Long deliveryPersonId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow();

        DeliveryPerson dp = deliveryPersonRepository
                .findById(deliveryPersonId)
                .orElseThrow();

        booking.setDeliveryPerson(dp);
        booking.setStatus(BookingStatus.ASSIGNED);

        bookingRepository.save(booking);
    }



    public void rejectBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);
    }

}
