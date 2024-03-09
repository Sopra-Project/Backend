package com.sopra.parkingsystem.service;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.sopra.parkingsystem.model.ParkingSpot;
import com.sopra.parkingsystem.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InspectorService {

    private final ParkingRepository parkingRepository;

    @Autowired
    public InspectorService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public JsonObject isCarRegistered(String registrationNumber) {
        LocalDateTime now = LocalDateTime.now();
        List<ParkingSpot> parkingSpots = parkingRepository.findByRegistrationNumber(registrationNumber, now);
        ParkingSpot parkingSpot = parkingSpots.isEmpty() ? null : parkingSpots.get(0);

        JsonObject jsonObject = new JsonObject();

        if (parkingSpot == null) {
            jsonObject.addProperty("message", "Car is not registered");
            jsonObject.addProperty("valid", false);
            return jsonObject;
        }

        if (parkingSpot.getStartTime().isAfter(now)) {
            jsonObject.addProperty("message", "Car is registered but not now");
            jsonObject.addProperty("valid", false);
            return jsonObject;
        }

        long timeLeft = parkingSpot.calculateMinutesLeft();

        if (timeLeft > 0) {
            jsonObject.addProperty("message", "Car is registered for building " + parkingSpot.getUser().getBuilding().getName() + "");
            jsonObject.addProperty("minutesLeft", timeLeft);
            jsonObject.addProperty("valid", true);
            return jsonObject;
        }

        jsonObject.addProperty("message", "Car is registered but has no time left");
        jsonObject.addProperty("valid", false);
        return jsonObject;
    }
}
