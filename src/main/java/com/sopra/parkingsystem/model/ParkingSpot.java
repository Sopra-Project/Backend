package com.sopra.parkingsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parkingspots")
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "regnumber")
    private String registrationNumber;
    @Column(name = "fromtime")
    private LocalDateTime startTime;
    @Column(name = "totime")
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
    @ManyToOne
    @JoinColumn(name = "parkingstatusid")
    private Status status;

    public long calculateMinutesLeft() {
        if (endTime == null || LocalDateTime.now().isAfter(endTime)) {
            return 0;
        } else {
            long secondsLeft = endTime.atZone(ZoneId.systemDefault()).toEpochSecond() - LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
            return secondsLeft / 60;
        }
    }

}
