package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.repository.UserRepo;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean registerUser(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username cannot be empty.");
            return false;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println("Password cannot be empty.");
            return false;
        }

        username = username.trim();

        if (userRepo.usernameExists(username)) {
            System.out.println("Username already exists.");
            return false;
        }

        return userRepo.registerUser(username, password);
    }

    @Override
    public User loginUser(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        return userRepo.loginUser(username, password);
    }

    @Override
    public User getUserById(int id) {

        if (id <= 0) {
            System.out.println("Invalid User ID.");
            return null;
        }
        return userRepo.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    @Override
    public boolean updateUserRole(int id, String role) {
        if (id <= 0) {
            System.out.println("Invalid User ID.");
            return false;
        }

        if (role == null || role.trim().isEmpty()) {
            System.out.println("Role cannot be empty.");
            return false;
        }

        role = role.trim().toLowerCase();

        if (!role.equals("admin") &&
                !role.equals("user")) {

            System.out.println("Invalid role. Use admin or user.");

            return false;
        }

        return userRepo.updateUserRole(id, role);
    }

    @Override
    public boolean deleteUser(int id) {

        if (id <= 0) {
            System.out.println("Invalid User ID.");
            return false;
        }

        return userRepo.deleteUser(id);
    }

    @Override
    public boolean usernameExists(String username) {

        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        return userRepo.usernameExists(username.trim());
    }
}