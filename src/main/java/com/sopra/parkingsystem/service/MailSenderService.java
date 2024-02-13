package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.model.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailSenderService {
    private final JavaMailSender mailSender;

    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Login to Parking System");
        String code = generateLoginCode();
        String content = "Dear " + user.getName() + ",\n\n" +
                "Please use the following code to login to the Parking System: " + code + "\n\n" +
                "Best regards,\n" +
                "Parking System";
        message.setText(content);

        mailSender.send(message);
    }

    private String generateLoginCode() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}
