package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.model.User;
import com.sopra.parkingsystem.model.UserCode;
import com.sopra.parkingsystem.model.dto.CodeAuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserService userService;
    private final TokenService tokenService;
    private final MailSenderService mailSenderService;
    private final UserCodeService userCodeService;

    @Autowired
    public AuthService(UserService userService, TokenService tokenService,
                       MailSenderService mailSenderService, UserCodeService userCodeService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.mailSenderService = mailSenderService;
        this.userCodeService = userCodeService;
    }

    public String login(String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            mailSenderService.sendEmail(user);
            return "Code sent to email";
        }
        return null;
    }

    public boolean validateCode(CodeAuthDTO dto) {
        UserCode userCode = userCodeService.getUserCodeByCode(dto.code);
        if (userCode == null) {
            return false;
        }
        if (!userCode.getUser().getEmail().equals(dto.email)) {
            return false;
        }
        userCodeService.delete(userCode);
        return !userCode.isExpired();
    }

    public String generateToken(String email) {
        User user = userService.getUserByEmail(email);
        return tokenService.encodeToken(user);
    }
}
