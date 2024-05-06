package com.example.mydorm.services;

import com.example.mydorm.models.User;
import com.example.mydorm.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    public void createRoom(String name, String bio, User admin) {
        roomRepository.insert(name, bio, admin);
    }

    public int getRoomId(int id) {
        return roomRepository.getRoomId(id);
        )
    }
}
