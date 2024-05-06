package com.example.mydorm.models;

import java.util.ArrayList;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean admin;
    private ArrayList<Room> rooms;
    private ArrayList<Room> adminRooms;


    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<Room> getAdminRooms() {
        return adminRooms;
    }

    public void setAdminRooms(ArrayList<Room> adminRooms) {
        this.adminRooms = adminRooms;
    }
}
