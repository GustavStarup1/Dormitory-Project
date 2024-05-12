package com.example.mydorm.services;

import com.example.mydorm.models.User;
import com.example.mydorm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void create(String firstName, String lastName, String password, String email) {
        userRepository.create(firstName,lastName,password,email);
    }

    public User login(String email, String password) {
        return userRepository.verifyUser(email,password);
    }

    public void update(User user, String email, String password) {
        userRepository.update(user, email, password);
    }


    public List<User> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword);
    }

    public User getUser(int id) {
        return userRepository.getUser(id);
    }
}
