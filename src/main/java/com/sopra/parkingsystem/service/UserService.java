package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.model.User;
import com.sopra.parkingsystem.model.dto.CreateUserDTO;
import com.sopra.parkingsystem.model.dto.EditUserDTO;
import com.sopra.parkingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsersByBuildingId(int id) {
        return userRepository.findByBuildingId(id);
    }

    public User createUser(CreateUserDTO dto, int buildingId) {
        User user = dto.toUser(buildingId);
        return save(user);
    }

    public int updateUser(EditUserDTO dto, int buildingId) {
        User user = userRepository.findById(dto.id).orElse(null);
        if (user == null) {
            return 0;
        }
        if (user.getBuilding().getId() != buildingId) {
            return 0;
        }
        return userRepository.update(dto.name, dto.email, dto.roleId, dto.id);
    }


    private User save(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

}
