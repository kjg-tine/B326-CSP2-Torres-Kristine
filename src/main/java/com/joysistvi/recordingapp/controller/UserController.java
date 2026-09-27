package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.service.UserService;

import java.util.List;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public boolean handleRegister(
            String username,
            String password
    ) {
        return userService.registerUser(
                username,
                password
        );
    }

    public User handleLogin(
            String username,
            String password
    ) {
        return userService.loginUser(
                username,
                password
        );
    }

    public User handleGetUserById(int id) {
        return userService.getUserById(id);
    }

    public List<User> handleViewAllUsers() {
        return userService.getAllUsers();
    }

    public boolean handleUpdateUserRole(
            int id,
            String role
    ) {
        return userService.updateUserRole(
                id,
                role
        );
    }

    public boolean handleDeleteUser(int id) {
        return userService.deleteUser(id);
    }

    public boolean handleUsernameExists(
            String username
    ) {
        return userService.usernameExists(
                username
        );
    }
}