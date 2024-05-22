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

    /**
     * For hver post kører metoderne setPostAttributes og checkForLike. setPostAttributes sætter author, usersLiked og comments
     * attributter for hver post. CheckForLike checker om brugeren der er logget ind har liket det.
     * @param PostId post id
     * @param userId user id
     * @return List<Post>
     */
    public List<Post> getPostsForRoom(int PostId, int userId) {
        List<Post> posts = postRepository.getPostsForRoom(PostId);
        for (Post post : posts){
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

    /*Sætter attributter for post objektet som ikke kan hentes med databasen*/
    public void setPostAttributes(Post post){
        post.setAuthor(userService.getUser(post.getProfileId()));
        post.setUsersLiked(postRepository.getLikes(post.getId()));
        post.setComments(postRepository.getComments(post.getId()));
        for (Comment comment : post.getComments()) {
            comment.setAuthor(userService.getUser(comment.getProfileId()));
        }
    }
}
