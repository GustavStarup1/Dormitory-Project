package com.example.mydorm.controllers;

import com.example.mydorm.models.User;
import com.example.mydorm.services.RoomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/room")
public class RoomController {
   @Autowired
    RoomService roomService;


   @GetMapping("/new")
    public String create() {
       return "home/create_room";
   }

   @PostMapping("/new")
    public String insert(HttpSession session, @RequestParam String name, @RequestParam String bio){
       User admin = (User)session.getAttribute("user");
       roomService.createRoom(name,bio,admin);
       return "redirect:/";
   }

   @GetMapping("/{id}")
    public String room(@PathVariable ("id") int roomID, Model model, HttpSession session) {
       User user = (User)session.getAttribute("user");
       model.addAttribute("user", user);
       roomID = roomService.getRoomId(user.getId());
       return "home/room";
   }

}
