package com.example.vinhomeproject.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendMailUtils {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendSimpleEmail(String to, String subject, String text) {
        // Tạo đối tượng SimpleMailMessage để cấu hình email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
