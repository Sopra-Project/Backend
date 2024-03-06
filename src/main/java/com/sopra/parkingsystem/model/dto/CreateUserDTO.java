package com.sopra.parkingsystem.model.dto;

import com.sopra.parkingsystem.model.Building;
import com.sopra.parkingsystem.model.Role;
import com.sopra.parkingsystem.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserDTO {
    public String name;
    public String email;
    public int roleId;


    public User toUser(int buildingId) {
        User user = User.builder()
                .name(name)
                .email(email.toLowerCase())
                .role(Role.builder()
                        .id(roleId)
                        .build())
                .building(Building.builder()
                        .id(buildingId)
                        .build())
                .build();

        return user;
    }
}
