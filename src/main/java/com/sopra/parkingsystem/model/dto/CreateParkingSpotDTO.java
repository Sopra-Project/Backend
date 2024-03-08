package com.sopra.parkingsystem.model.dto;

import com.sopra.parkingsystem.model.ParkingSpot;
import com.sopra.parkingsystem.model.Status;
import com.sopra.parkingsystem.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateParkingSpotDTO {
    public String registrationNumber;
    public LocalDateTime startTime;
    public LocalDateTime endTime;

    public ParkingSpot toParkingSpot(int userId) {
        return ParkingSpot.builder()
                .registrationNumber(registrationNumber)
                .startTime(startTime)
                .endTime(endTime)
                .user(User.builder()
                        .id(userId)
                        .build())
                .status(Status.builder()
                        .id(1)
                        .build())
                .build();
    }

    public ParkingSpot toParkingSpot() {
        return ParkingSpot.builder()
                .registrationNumber(registrationNumber)
                .startTime(startTime)
                .endTime(endTime)
                .status(Status.builder()
                        .id(1)
                        .build())
                .build();
    }
}
