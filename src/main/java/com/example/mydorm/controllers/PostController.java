package com.example.mydorm.controllers;

import com.example.mydorm.models.Comment;
import com.example.mydorm.models.Post;
import com.example.mydorm.models.User;
import com.example.mydorm.services.PostService;
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
    private PostService postService;



    @PostMapping("room/{id}/new_post")
    public String createPost(HttpSession session, @PathVariable("id") int roomId, @RequestParam String text) {
        User user = (User) session.getAttribute("user");
        postService.createPost(roomId, user.getId(), text);
        return "redirect:/room/{id}";
    }

    @GetMapping("room/{id}/post/{post_id}/delete")
    public String confirmDeletion(HttpSession session, @PathVariable("id") int roomId, @PathVariable("post_id") int postId, Model model) {
        User user = (User) session.getAttribute("user");
        Post post = postService.getPost(postId);
        model.addAttribute("roomId", roomId);
        if (user.getId() == post.getAuthor().getId() || user.isAdmin()) { /*hvis den bruger som er logget ind er forfatter eller admin kan de slette*/
            model.addAttribute("post", post);
            return "home/post_delete";
        } else {
            return "redirect:/room/{id}";
        }
    }

    @PostMapping("room/{id}/post/{post_id}/delete")
    public String delete(@PathVariable("post_id") int postId) {
        postService.delete(postId);
        return "redirect:/room/{id}";
    }

    @PostMapping("room/{id}/post/{post_id}/like")
    public String like(@PathVariable("post_id")int postId, @PathVariable("id") int roomid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post post = postService.getPost(postId, user.getId());
        System.out.println(post.isLiked());
        if (post.isLiked()){
            postService.removeLike(postId, user.getId());
        } else {
            postService.likePost(postId, user.getId());        }
        return "redirect:/room/{id}";
    }


    @PostMapping("/room/{id}/post/{post_id}/comment")
    public String comment(@PathVariable("post_id")int postId, @PathVariable("id") int roomid, HttpSession session, @RequestParam String text){
        User user = (User) session.getAttribute("user");
        postService.addComment(postId, user.getId(), text);
        return "redirect:/room/{id}";
    }
}