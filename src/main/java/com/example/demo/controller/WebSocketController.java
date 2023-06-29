package com.example.demo.controller;

import com.example.demo.system.UserSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketController {

    @RequestMapping("/websocket/StaffChat")
    public String tostaffChat( Model model ) {
        model.addAttribute("customer", UserSystem.customer);
        return "staffChat";
    }
    @RequestMapping("/websocket/CustomerChat")
    public String tocustomerChat() {
        return "customerChat";
    }

}