package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.User;

import java.util.List;

public interface UserRepo {

    boolean registerUser(
            String username,
            String password
    );

    User loginUser(
            String username,
            String password
    );

    User getUserById(int id);

    List<User> getAllUsers();

    boolean updateUserRole(
            int id,
            String role
    );

    boolean deleteUser(
            int id
    );

    boolean usernameExists(
            String username
    );
}