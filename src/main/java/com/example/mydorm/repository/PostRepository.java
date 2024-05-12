package com.example.mydorm.repository;

import com.example.mydorm.models.Post;
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
}
