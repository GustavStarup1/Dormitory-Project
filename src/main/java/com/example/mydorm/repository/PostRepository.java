package com.example.mydorm.repository;

import com.example.mydorm.models.Post;
import com.example.mydorm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void create(int profileId, int roomId, String text) {
        String query = "INSERT INTO post (profile_id, room_id, text) VALUES (?,?,?)";
        jdbcTemplate.update(query, roomId, profileId, text);
    }

    public List<Post> getPostsForRoom(int id) {
        String query = "SELECT * FROM post WHERE room_id = ? ORDER BY creation_date";
        RowMapper<Post> rowMapper = new BeanPropertyRowMapper<>(Post.class);
        return jdbcTemplate.query(query, rowMapper, id);
    }
    public void delete(int postId) {
        String query = "DELETE FROM post WHERE id = ?";
        jdbcTemplate.update(query, postId);
    }

    public Post getPost(int postId) {
        String query = "SELECT * FROM post WHERE id = ?;";
        RowMapper<Post> rowMapper = new BeanPropertyRowMapper<>(Post.class);
        return jdbcTemplate.queryForObject(query, rowMapper, postId);
    }

    public List<User> getLikes(int postId) {
        String query = "SELECT * FROM profile INNER JOIN post_likes ON profile.id = post_likes.profile_id WHERE post_id = ?;";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        List<User> users = jdbcTemplate.query(query, rowMapper, postId);
        return users;
    }


    public void insertIntoPostLikes(int postId, int userId) {
        String query = "INSERT INTO post_likes (post_id, profile_id) VALUES (?,?)";
        jdbcTemplate.update(query, postId, userId);
    }
}
