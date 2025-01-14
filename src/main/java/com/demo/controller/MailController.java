package com.demo.controller;

import jakarta.mail.MessagingException;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.demo.service.MailService;

@Controller
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @PostMapping("/mail")
    public String sendMail(Model model, 
                           @RequestParam String to,
                           @RequestParam String subject,
                           @RequestParam String msg,
                           @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        try {
            mailService.mailSending(to, msg, subject, file);
            model.addAttribute("success", "Mail sent successfully to: " + to);
        } catch (MessagingException | IllegalStateException e) {
            model.addAttribute("error", "Error while sending mail: " + e.getMessage());
        }
        return "redirect:/";
    }
}
