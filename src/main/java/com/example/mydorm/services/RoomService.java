package com.example.mydorm.services;

import com.example.mydorm.models.Room;
import com.example.mydorm.models.User;
import com.example.mydorm.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    public void createRoom(String name, String bio, User admin) {
        roomRepository.insert(name, bio, admin);
    }

    public Room getRoom(int roomId, int userID ) {
        return roomRepository.getRoom(roomId, userID);
    }

    public List<Room> getRooms(int id) {
        return roomRepository.getRooms(id);
    }

    public void updateRoom(int id, String name, String bio) {
        roomRepository.updateRoom(id, name, bio);
    }

    public void inviteUsers(int roomId, User user) {
        roomRepository.insertUsers(roomId, user);
    }
}
