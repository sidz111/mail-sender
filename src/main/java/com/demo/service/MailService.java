package com.demo.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	
    @Autowired
    private JavaMailSender javaMailSender;

    public void mailSending(String sendTo, String text, String subject, MultipartFile file) throws MessagingException, IllegalStateException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);  // true for multipart (attachment)

        helper.setFrom("sssurwade2212@gmail.com");

        // Splitting multiple recipients by comma
        String[] recipients = sendTo.split(",");
        helper.setTo(recipients);

        helper.setSubject(subject);
        
        // Enable HTML format for the message
        helper.setText(text, true);

        // If a file is attached, add it
        if (file != null && !file.isEmpty()) {
            File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(tempFile);
            FileSystemResource attachment = new FileSystemResource(tempFile);
            helper.addAttachment(file.getOriginalFilename(), attachment);
        }

        javaMailSender.send(message);
    }
}
