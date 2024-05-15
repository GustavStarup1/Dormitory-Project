package com.example.mydorm.services;

import com.example.mydorm.models.Comment;
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
            setPostAttributes(post);
            checkForLike(post, userId);
        }
        return posts;
    }

    public void delete(int postId) {
        postRepository.delete(postId);
    }

    public Post getPost(int postId){
        Post post = postRepository.getPost(postId);
        setPostAttributes(post);
        return post;
    }
    public Post getPost(int postId, int userId){
        Post post = postRepository.getPost(postId);
        setPostAttributes(post);
        checkForLike(post, userId);
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

    public void removeLike(int postId, int id) {
        postRepository.deleteFromPostLikes(postId, id);
    }

    public void addComment(int postId, int userId, String text) {
        postRepository.insertComment(postId,userId,text);

    }

    public void setPostAttributes(Post post){
        post.setAuthor(userService.getUser(post.getProfileId()));
        post.setUsersLiked(postRepository.getLikes(post.getId()));
        post.setComments(postRepository.getComments(post.getId()));
        for (Comment comment : post.getComments()) {
            comment.setAuthor(userService.getUser(comment.getProfileId()));
            /*lav likes senere*/
        }
    }
}
