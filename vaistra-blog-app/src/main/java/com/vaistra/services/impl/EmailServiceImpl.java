package com.vaistra.services.impl;

import com.vaistra.services.EmailService;
import com.vaistra.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    private final AppUtils appUtilsComment;

    @Value("${host_url}")
    private String host;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender, AppUtils appUtilsComment) {
        this.emailSender = emailSender;
        this.appUtilsComment = appUtilsComment;
    }

    @Override
    public void sendSimpleMailMessage(String name, String to) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("FORGET PASSWORD VERIFICATION");
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(appUtilsComment.getEmailMessage(name, host));
            emailSender.send(message);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
