package com.example.mydorm.repository;

import com.example.mydorm.models.*;
import com.example.mydorm.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        String insertRoomProfileQuery = "INSERT INTO room_profile (room_id, profile_id, admin) VALUES (?, ?, 1);";
        jdbcTemplate.update(insertRoomProfileQuery, roomId, adminId);
    }

    public List<Room> getRooms(int id) {
        String query = "SELECT * FROM room " + // Vælg alle kolonner fra tabellen "room"
                "INNER JOIN room_profile ON room.id = room_profile.room_id " + // laver en INNER JOIN mellem "room" og "room_profile" baseret på rum-id
                "WHERE room_profile.profile_id = ?;"; // Filtrer resultater baseret på brugerprofilens id
        RowMapper<Room> rowMapper = new BeanPropertyRowMapper<>(Room.class);
        return jdbcTemplate.query(query, rowMapper, id);
    }

    public Room getRoom(int roomId, int userId) {
        String query = "SELECT * FROM room " +
                "INNER JOIN room_profile ON room.id = room_profile.room_id " +
                "WHERE room_profile.room_id = ? AND room_profile.profile_id = ?;";
        RowMapper<Room> rowMapper = new BeanPropertyRowMapper<>(Room.class);
        return jdbcTemplate.queryForObject(query, rowMapper, roomId, userId);
    }

    public Room getRoom(int roomId) {
        String query = "SELECT * FROM room WHERE id = ?;";
        RowMapper<Room> rowMapper = new BeanPropertyRowMapper<>(Room.class);
        return jdbcTemplate.queryForObject(query, rowMapper, roomId);
    }

    public void updateRoom(int id, String name, String bio) {
            String query = "UPDATE mydorm.room SET name = ?, bio = ? WHERE id = ?;";
            jdbcTemplate.update(query, name, bio, id);
    }
}
