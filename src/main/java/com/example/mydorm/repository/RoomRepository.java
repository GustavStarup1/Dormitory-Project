package com.example.mydorm.repository;

import com.example.mydorm.models.Room;
import com.example.mydorm.models.int;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RoomRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public void insert(String name, String bio, User admin) {
        int adminId = admin.getId();
        String query = "INSERT INTO room (name, bio) VALUES (?,?);";
        jdbcTemplate.update(query, name, bio);

        // Hent det nyoprettede rums ID
        String getRoomIdQuery = "SELECT LAST_INSERT_ID()";
        Integer roomId = jdbcTemplate.queryForObject(getRoomIdQuery, Integer.class);

        // Tilknyt administratorprofilen til rummet
        String insertRoomProfileQuery = "INSERT INTO room_profile (room_id, profile_id, is_admin) VALUES (?, ?, 1);";
        jdbcTemplate.update(insertRoomProfileQuery, roomId, adminId);
    }


    public int getRoomId(int id) {
        String query = "SELECT * FROM room_profile WHERE profile_id = ?;";
        RowMapper<Room> rowMapper = new BeanPropertyRowMapper<>(Room.class);
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }
}
