package com.example.mydorm.controllers;

import com.example.mydorm.models.Room;
import com.example.mydorm.models.User;
import com.example.mydorm.services.RoomService;
import com.example.mydorm.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
@Autowired
    UserService userService;
@Autowired
    RoomService roomService;

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", user);
            model.addAttribute("user", session.getAttribute("user"));
            model.addAttribute("admin", user.isAdmin());
            List<Room> rooms = roomService.getRooms(user.getId());
            model.addAttribute("rooms", rooms);
        }

        return "home/index";
    }

    @GetMapping("/homepage")
    public String homepage(){
        return "home/homepage";
    }

}