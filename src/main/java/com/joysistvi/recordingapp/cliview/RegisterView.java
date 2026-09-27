package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.UserController;

import java.util.Scanner;

public class RegisterView {

    private final UserController userController;
    private final Scanner scanner;

    public RegisterView(
            UserController userController,
            Scanner scanner
    ) {
        this.userController = userController;
        this.scanner = scanner;
    }

    public void register() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("             USER REGISTER");
        System.out.println("========================================");

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }

        if (userController.handleUsernameExists(username)) {
            System.out.println("Username already exists.");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return;
        }

        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine().trim();

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match.");
            return;
        }

        boolean success = userController.handleRegister(
                username,
                password
        );

        if (success) {
            System.out.println();
            System.out.println("Registration successful!");

        } else {
            System.out.println();
            System.out.println("Registration failed.");
        }
    }
}