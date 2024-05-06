package com.example.mydorm.repository;

import com.example.mydorm.models.User;
import jdk.jfr.Registered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void create(String firstName, String lastName, String password, String email) {
        String query = "INSERT INTO profile (first_name, last_name, email, password) VALUES (?,?,?,?)";
        jdbcTemplate.update(query, firstName,lastName,email,password);
    }

    public User verifyUser(String email, String password) {
        String query = "SELECT * FROM mydorm.profile where email = ? and password = ?;";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        try {
        return jdbcTemplate.queryForObject(query, rowMapper, email, password);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(User user, String email, String password) {
        int id = user.getId();
        String query = "UPDATE mydorm.profile SET email = ?, password = ? WHERE id = ?;";
        jdbcTemplate.update(query,email, password, id);
    }
}