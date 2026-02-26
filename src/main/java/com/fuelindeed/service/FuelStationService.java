package com.fuelindeed.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.fuelindeed.entity.FuelStation;
import com.fuelindeed.enums.StationStatus;
import com.fuelindeed.repository.FuelStationRepository;
@Service
public class FuelStationService {
	
	private final FuelStationRepository fuelStationRepository;

    public FuelStationService(FuelStationRepository fuelStationRepository) {
        this.fuelStationRepository = fuelStationRepository;
    }

    // Register
    public FuelStation register(FuelStation station) {
        station.setStatus(StationStatus.PENDING);
        return fuelStationRepository.save(station);
    }

    // Login
    

       

    // ✅ APPROVE STATION
    public void approveStation(Long id) {
        FuelStation station = fuelStationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        station.setStatus(StationStatus.APPROVED);
        fuelStationRepository.save(station);
    }

    // ✅ REJECT STATION
    public void rejectStation(Long id) {
        FuelStation station = fuelStationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        station.setStatus(StationStatus.REJECTED);
        fuelStationRepository.save(station);
    }
    
    public FuelStation updateFuelDetails(
            Long stationId,
            double petrolQty,
            double petrolRate,
            boolean petrolAvailable,
            double dieselQty,
            double dieselRate,
            boolean dieselAvailable
    ) {
        FuelStation station = fuelStationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found"));

        if (station.getStatus() != StationStatus.APPROVED) {
            throw new RuntimeException("Station not approved");
        }

        station.setPetrolQty(petrolQty);
        station.setPetrolRate(petrolRate);
        station.setPetrolAvailable(petrolAvailable);

        station.setDieselQty(dieselQty);
        station.setDieselRate(dieselRate);
        station.setDieselAvailable(dieselAvailable);

        station.setLastUpdated(LocalDateTime.now());

        return fuelStationRepository.save(station);
    }
    
    public FuelStation getStationById(Long id) {
        return fuelStationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not found"));
    }

}
