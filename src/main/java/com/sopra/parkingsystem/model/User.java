package com.sopra.parkingsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "email", unique = true)
    private String email;
    @ManyToOne
    @JoinColumn(name = "roleid")
    private Role role;
    @Column(name = "totalparkingspots")
    private int totalparkingspots;
    @Column(name = "totalparkingspotsavailable")
    private int totalparkingspotsavailable;
}
