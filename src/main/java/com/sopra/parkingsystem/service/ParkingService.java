package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.model.ParkingSpot;
import com.sopra.parkingsystem.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public void unpark(int id) {
        parkingRepository.unpark(id);
    }

    public ParkingSpot getById(long id) {
        return parkingRepository.findById(id).orElse(null);
    }

//    public List<ParkingSpot> getFreeParkingSpots(Date date) {
//        return parkingRepository.getFreeParkingSpots(date);
//    }

    private int isParkingFull(ParkingSpot parking) {
        return parkingRepository.getNumberOfParkedCars(parking.getUser().getBuilding().getId(), parking.getStartTime(), parking.getEndTime());
    }

    public void save(ParkingSpot parkingSpot) {
        if (isParkingFull(parkingSpot) < parkingSpot.getUser().getBuilding().getTotalParkingSpots()) {
            parkingRepository.save(parkingSpot);
        } else {
            throw new RuntimeException("Parking is full");
        }
    }

    public void delete(ParkingSpot parkingSpot) {
        parkingRepository.delete(parkingSpot);
    }

    public List<ParkingSpot> getParkingSpotsByUserId(int userId) {
        return parkingRepository.findByUserId(userId);
    }
}
