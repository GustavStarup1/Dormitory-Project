package com.example.mydorm.controllers;

import com.example.mydorm.models.Post;
import com.example.mydorm.models.User;
import com.example.mydorm.services.PostService;
import com.example.mydorm.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

@GetMapping("room/{id}/feed")
    public String feed(@PathVariable("id") int id, Model model){
    List<Post> posts = postService.getPostsForRoom(id);
    model.addAttribute("posts", posts);
return "home/room_posts";
}

@PostMapping("room/{id}/new_post")
    public String createPost(HttpSession session, @PathVariable("id") int roomId, @RequestParam String text) {
    User user = (User)session.getAttribute("user");
    postService.createPost(roomId, user.getId(), text);
    return "redirect:/room/{id}";
}






}
