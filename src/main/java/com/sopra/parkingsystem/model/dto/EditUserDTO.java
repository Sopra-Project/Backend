package com.sopra.parkingsystem.model.dto;

import com.sopra.parkingsystem.model.Building;
import com.sopra.parkingsystem.model.Role;
import com.sopra.parkingsystem.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditUserDTO {
    public int id;
    public String name;
    public String email;
    public int roleId;

    public User toUser(int buildingId) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        Role role = new Role();
        role.setId(roleId);
        user.setRole(role);
        Building building = new Building();
        building.setId(buildingId);
        user.setBuilding(building);
        return user;
    }
}
