package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.model.ParkingSpot;
import com.sopra.parkingsystem.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public List<ParkingSpot> getAllParkingSpots() {
        return parkingRepository.findAll();
    }
}
