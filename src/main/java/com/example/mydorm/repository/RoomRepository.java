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
/**
 * Der stod på nettet at man kunne bruge transactional hvis man bruger flere queries i én metode, for at mindske fejl
 * hvor flere bruger sender flere queries af sted samtidigt.
 */
    @Transactional
    public void insert(String name, String bio, User admin) {
        int adminId = admin.getId();
        String query = "INSERT INTO room (name, bio) VALUES (?,?);";
        jdbcTemplate.update(query, name, bio);

        /*Henter det nyoprettede rums ID*/
        String getRoomIdQuery = "SELECT LAST_INSERT_ID()"; /*henter id fra det sidste der blev insertet*/
        Integer roomId = jdbcTemplate.queryForObject(getRoomIdQuery, Integer.class);

        /*Tilknyt admin til rummet*/
        String insertRoomProfileQuery = "INSERT INTO room_profile (room_id, profile_id, admin) VALUES (?, ?, 1);";
        jdbcTemplate.update(insertRoomProfileQuery, roomId, adminId);
    }

    public void insertUsers(int roomId, User user){
            int userId = user.getId();
            String query = "INSERT INTO room_profile (room_id, profile_id) VALUES (?, ?);";
            jdbcTemplate.update(query, roomId, userId);


    }

    public List<Room> getRooms(int id) {
        String query = "SELECT * FROM room " +
                "INNER JOIN room_profile ON room.id = room_profile.room_id " +
                "WHERE room_profile.profile_id = ?;";
        RowMapper<Room> rowMapper = new BeanPropertyRowMapper<>(Room.class);
        return jdbcTemplate.query(query, rowMapper, id);
    }

    /**
     * Returnerer et rum. Modtager user id og room id for at lave en inner join med room_profile.
     * På den måde henter den rummet og om brugeren er administrator for rumme, gennem room_profile, som har en admin column.
     * @param roomId rummet som man vil tilgå.
     * @param userId brugeren som er logget ind.
     * @return jdbcTemplate.queryForObject
     */
    public Room getRoom(int roomId, int userId) {
        String query = "SELECT * FROM room " +
                "INNER JOIN room_profile ON room.id = room_profile.room_id " +
                "WHERE room_profile.room_id = ? AND room_profile.profile_id = ?;";
        RowMapper<Room> rowMapper = new BeanPropertyRowMapper<>(Room.class);
        return jdbcTemplate.queryForObject(query, rowMapper, roomId, userId);
    }


    public void updateRoom(int id, String name, String bio) {
            String query = "UPDATE mydorm.room SET name = ?, bio = ? WHERE id = ?;";
            jdbcTemplate.update(query, name, bio, id);
    }
}
