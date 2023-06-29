package com.example.demo.controller;

import com.example.demo.services.MailService;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 测试邮件发送
 * @author qzz
 */
@RestController
public class SendMailController {

    @Autowired
    private MailService mailService;


    @RequestMapping("/sendTextMail")
    public void sendTextMail(
            HttpServletRequest request,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "message") String message,
            Model model
    ) throws IOException {
        String txt = message + "\n" +  "\n" +"User name: " + username + "\n" + "Email: " + email + "\n" + "Phone: " + phone;

        mailService.sendTextMailMessage("3374618571@qq.com",subject,txt);
    }
}

