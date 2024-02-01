package com.sopra.parkingsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parkingstatus")
public class Status {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name")
    private String name;
}
