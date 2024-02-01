package com.sopra.parkingsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parkingspots")
public class ParkingSpot {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "regnumber")
    private int registrationNumber;
    @Column(name = "fromtime")
    private LocalDate startTime;
    @Column(name = "totime")
    private LocalDate endTime;
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
    @ManyToOne
    @JoinColumn(name = "parkingstatusid")
    private Status status;
}
