package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.model.UserCode;
import com.sopra.parkingsystem.repository.UserCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCodeService {
    private final UserCodeRepository userCodeRepository;

    @Autowired
    public UserCodeService(UserCodeRepository userCodeRepository) {
        this.userCodeRepository = userCodeRepository;
    }

    public void save(UserCode userCode) {
        userCodeRepository.saveAndFlush(userCode);
    }

    public List<UserCode> getUserCodeByCode(String code) {
        return userCodeRepository.findByCode(code);
    }

    public void delete(UserCode userCode) {
        userCodeRepository.delete(userCode);
    }

}
