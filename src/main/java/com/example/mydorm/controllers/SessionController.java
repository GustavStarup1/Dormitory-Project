package com.example.mydorm.controllers;

import com.example.mydorm.models.Room;
import com.example.mydorm.models.User;
import com.example.mydorm.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "home/login";
    }

    @PostMapping("/login")
    public String login(HttpSession session, @RequestParam String email, @RequestParam String password, Model model){
        User user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            return "home/login_correct";
        }else{
            model.addAttribute("user", false);
            return "home/login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


}
