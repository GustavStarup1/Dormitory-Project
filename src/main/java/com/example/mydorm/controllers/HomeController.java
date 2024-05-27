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

    @GetMapping("/about/rooms")
    public String aboutRooms(){
        return "home/about_rooms";
    }

    @GetMapping("/about/kitchens")
    public String aboutKithens(){
        return "home/about_kitchens";
    }

    @GetMapping("/about/application")
    public String aboutApplication(){
        return "home/about_application";
    }

    @GetMapping("/about/faqs")
    public String aboutFaqs(){
        return "home/about_faqs";
    }

    @GetMapping("/about/community")
    public String community(){
        return "home/about_community";
    }

    @GetMapping("/rules/house_rules")
    public String houseRules(){
        return "home/rules_terms";
    }


    @GetMapping("/rules/internal_relocation")
    public String internalRelocation(){
        return "home/rules_internal_relocation";
    }

    @GetMapping("/rules/subleasing")
    public String subleasing(){
        return "home/rules_subleasing";
    }



}
