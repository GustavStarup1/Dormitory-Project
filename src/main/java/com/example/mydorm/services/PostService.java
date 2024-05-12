package com.example.mydorm.services;

import com.example.mydorm.models.Post;
import com.example.mydorm.models.User;
import com.example.mydorm.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired /*må jeg gerne have dem begge autowired*/
    UserService userService;


    public void createPost(int roomId, int userId, String text) {
        postRepository.create(roomId, userId, text);
    }

    public List<Post> getPostsForRoom(int id) {
        List<Post> posts = postRepository.getPostsForRoom(id);
        for (Post post : posts){
            int profileId = post.getProfileId();
            User author = userService.getUser(profileId);
            post.setAuthor(author);
        }
        return posts;
    }
}
