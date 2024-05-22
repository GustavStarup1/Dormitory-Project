package com.example.mydorm.controllers;

import com.example.mydorm.models.User;
import com.example.mydorm.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Random;

@Controller
public class UserController {
    private boolean edit;
    @Autowired
UserService userService;


    @GetMapping("/new_user")
    public String createLogin(Model model) {
        String password = generatePassword();
        model.addAttribute("password", password);
        return "home/create_user";
    }
    @PostMapping("/new_user")
    public String newUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email,@RequestParam String password) {
        userService.create(firstName,lastName,password,email);
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("edit", edit); /*gør at felter er redigerbare efter om edit er true eller false*/
        if (session.getAttribute("user") != null){
            return "home/profile";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/profile/edit")
    public String editProfile() {
        edit = true; /*Nu vil home/profile vise redigerings felter*/
        return "redirect:/profile";
    }

    @PostMapping("/profile/save")
    public String save(HttpSession session, @RequestParam String email, @RequestParam String password) {
        User user = (User)session.getAttribute("user");
        userService.update(user, email, password);
        edit = false;
        return "redirect:/profile";
    }


/*laver et tilfældigt password som brugeren skal bruge første gang de logger ind og så kan de ændre det*/
    private String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

}
