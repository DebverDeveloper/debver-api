package com.donatoordep.debver.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;

    @Value("${support.mail}")
    private String supportMail;

    public void sendEmailToClient(String subject, String email, String content) throws MessagingException {

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setTo(email);
        helper.setFrom(supportMail);
        helper.setText(content, true);
        helper.setSubject(subject);

        sender.send(mimeMessage);
    }
}