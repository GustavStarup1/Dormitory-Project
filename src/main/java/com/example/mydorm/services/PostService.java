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

    public List<Post> getPostsForRoom(int id, int userId) {
        List<Post> posts = postRepository.getPostsForRoom(id);
        for (Post post : posts){   /*giver hver post en forfatter som er User objekt*/
            int profileId = post.getProfileId();
            User author = userService.getUser(profileId);
            post.setAuthor(author);
            post.setUsersLiked(postRepository.getLikes(post.getId()));
            checkForLike(post, userId);
        }
        return posts;
    }

    public void delete(int postId) {
        postRepository.delete(postId);
    }

    public Post getPost(int postId){
        Post post = postRepository.getPost(postId);
        post.setAuthor(userService.getUser(post.getProfileId()));
        post.setUsersLiked(postRepository.getLikes(post.getId()));
        return post;
    }

    public void likePost(int postId, int userId) {
        postRepository.insertIntoPostLikes(postId, userId);
    }


    /*Tjekker om brugeren har liket opslaget*/
    public void checkForLike(Post post, int userId) {
        for (User user : post.getUsersLiked()) {
            if (user.getId() == userId) {
                post.setLiked(true);
            }
        }
    }
}
