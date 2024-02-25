package com.sopra.parkingsystem.model.dto;

import com.sopra.parkingsystem.model.Building;
import com.sopra.parkingsystem.model.Role;
import com.sopra.parkingsystem.model.User;
import lombok.Data;

@Data
public class CreateAdminUser {
    public String name;
    public String email;
    public int buildingId;

    public User toUser() {
        return User.builder()
                .name(name)
                .email(email)
                .building(Building.builder()
                        .id(buildingId)
                        .build())
                .role(Role.ADMIN)
                .build();
    }
}
