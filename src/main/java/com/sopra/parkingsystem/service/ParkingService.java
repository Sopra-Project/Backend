package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.controller.CreateParkingSpotDTO;
import com.sopra.parkingsystem.model.ParkingSpot;
import com.sopra.parkingsystem.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.util.stream.Collectors.groupingBy;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }


    public void unpark(int id) {
        parkingRepository.unpark(id);
    }

    public ParkingSpot getById(int id) {
        return parkingRepository.findById(id).orElse(null);
    }

    private int getParkedCarsWithDate(ParkingSpot parking) {
        return parkingRepository.getNumberOfParkedCars(
                parking.getUser().getBuilding().getId(),
                parking.getStartTime(),
                parking.getEndTime()
        );
    }

    public List<ParkingSpot> getParkingSpotsFromToFromBuilding(LocalDateTime from, LocalDateTime to, int buildingId) {
        return parkingRepository.findParkingSpotsFromToFromBuilding(from, to, buildingId);
    }

    public Map<Integer, Map<Integer, List<ParkingSpot>>> getAllYearsParkingSpotsFromToFromBuilding(int buildingId) {
        LocalDateTime from = LocalDateTime.now().withDayOfMonth(1);
        LocalDateTime to = LocalDateTime.now().with(lastDayOfYear());
        List<ParkingSpot> parkingSpots = getParkingSpotsFromToFromBuilding(from, to, buildingId);
        return parkingSpots.stream().collect(groupingBy(p -> p.getStartTime().getMonth().getValue(), groupingBy(p -> p.getStartTime().getDayOfMonth())));
    }

    public ParkingSpot addParkingSpot(CreateParkingSpotDTO dto, int userId) {
        ParkingSpot parkingSpot = dto.toParkingSpot(userId);
        return parkingRepository.save(parkingSpot);
    }

    public void deleteParkingSpot(int id) {
        parkingRepository.deleteById(id);
    }

    public void updateParkingSpot(CreateParkingSpotDTO dto, int id) {
        ParkingSpot parkingSpot = dto.toParkingSpot();
        parkingSpot.setId(id);
        parkingRepository.update(dto.startTime, dto.endTime, dto.registrationNumber, id);
    }


    public void save(ParkingSpot parkingSpot) {
        if (getParkedCarsWithDate(parkingSpot) < parkingSpot.getUser().getBuilding().getTotalParkingSpots()) {
            parkingRepository.save(parkingSpot);
        } else {
            throw new RuntimeException("Parking is full");
        }
    }

    public void delete(ParkingSpot parkingSpot) {
        parkingRepository.delete(parkingSpot);
    }

    public List<ParkingSpot> getParkingSpotsByBuildingId(int buildingId) {
        return parkingRepository.findByBuildingId(buildingId);
    }
}
