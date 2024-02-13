package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.model.User;
import com.sopra.parkingsystem.model.UserCode;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    private final JavaMailSender mailSender;
    private final UserCodeService userCodeService;

    public MailSenderService(JavaMailSender mailSender, UserCodeService userCodeService) {
        this.mailSender = mailSender;
        this.userCodeService = userCodeService;
    }

    public void sendEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Login to Parking System");
        UserCode userCode = new UserCode(user);
        userCodeService.save(userCode);
        String content = "Dear " + user.getName() + ",\n\n" +
                "Please use the following code to login to the Parking System: " + userCode.getCode() + "\n\n" +
                "Best regards,\n" +
                "Parking System";
        message.setText(content);

        mailSender.send(message);
    }
}
