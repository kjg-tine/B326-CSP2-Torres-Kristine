package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.UserController;
import com.joysistvi.recordingapp.model.User;

import java.util.Scanner;

public class LoginView {

    private final UserController userController;
    private final Scanner scanner;

    public LoginView(
            UserController userController,
            Scanner scanner
    ) {
        this.userController = userController;
        this.scanner = scanner;
    }

    public User login() {

        System.out.println();
        System.out.println(
                "========================================"
        );
        System.out.println(
                "              USER LOGIN"
        );
        System.out.println(
                "========================================"
        );

        System.out.print("Username: ");

        String username =
                scanner.nextLine().trim();

        if (username.isEmpty()) {

            System.out.println(
                    "Username cannot be empty."
            );

            return null;
        }

        System.out.print("Password: ");

        String password =
                scanner.nextLine().trim();

        if (password.isEmpty()) {

            System.out.println(
                    "Password cannot be empty."
            );

            return null;
        }

        User user =
                userController.handleLogin(
                        username,
                        password
                );

        if (user != null) {

            System.out.println();
            System.out.println(
                    "Login successful!"
            );

            System.out.println(
                    "Welcome, "
                            + user.getUsername()
                            + "!"
            );

            System.out.println(
                    "Role: "
                            + user.getRole()
            );

            return user;
        }

        System.out.println();
        System.out.println(
                "Invalid username or password."
        );

        return null;
    }
}