package com.example.mydorm.repository;

import com.example.mydorm.models.Room;
import com.example.mydorm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void create(String firstName, String lastName, String password, String email) {
        String query = "INSERT INTO profile (first_name, last_name, email, password) VALUES (?,?,?,?)";
        jdbcTemplate.update(query, firstName, lastName, email, password);
    }

    public User verifyUser(String email, String password) {
        String query = "SELECT * FROM mydorm.profile where email = ? and password = ?;";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        List<User> users = jdbcTemplate.query(query, rowMapper, email, password);
        if (!users.isEmpty()) {
            User user = users.get(0);
            getUserWithRooms(user);
            return user;
        } else {
            return null;
        }
    }

    public void update(User user, String email, String password) {
        int id = user.getId();
        String query = "UPDATE mydorm.profile SET email = ?, password = ? WHERE id = ?;";
        jdbcTemplate.update(query, email, password, id);
    }

    public void getUserWithRooms(User user) {
        // Hent brugerens medlemsrum fra databasen
        int id = user.getId();
        String memberRoomsQuery = "SELECT * FROM room " +
                "INNER JOIN room_profile ON room.id = room_profile.room_id " +
                "WHERE room_profile.profile_id = ?";
        List<Room> memberRooms = jdbcTemplate.query(memberRoomsQuery, new BeanPropertyRowMapper<>(Room.class), id);
        user.setRooms(memberRooms);

        // Hent brugerens administratorrum fra databasen
        String adminRoomsQuery = "SELECT * FROM room " +
                "INNER JOIN room_profile ON room.id = room_profile.room_id " +
                "WHERE room_profile.profile_id = ? AND room_profile.admin = 1";
        List<Room> adminRooms = jdbcTemplate.query(adminRoomsQuery, new BeanPropertyRowMapper<>(Room.class), id);
        user.setAdminRooms(adminRooms);
    }


    public List<User> searchUsers(String keyword) {
        if (keyword.contains(" ")){
            String[] name = keyword.split(" ");
            String firstName = name[0];
            String lastName = name[1];
            String query = "SELECT * FROM profile WHERE first_name LIKE ? OR last_name LIKE ?";
            RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);        /*find ud af hvad fucker der foregår*/
            return jdbcTemplate.query(query, rowMapper, "%" + firstName + "%", "%" + lastName + "%");
        } else {
            String query = "SELECT * FROM profile WHERE first_name LIKE ?";
            RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);        /*find ud af hvad fucker der foregår*/
            return jdbcTemplate.query(query, rowMapper, "%" + keyword + "%");
        }
    }


    public User getUser(int id) {
        String query = "SELECT * FROM profile WHERE id = ?;";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        User user = jdbcTemplate.queryForObject(query, rowMapper, id);
        if (user != null) {
            getUserWithRooms(user);
        }
        return user;
    }
}

