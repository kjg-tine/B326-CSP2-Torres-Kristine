package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.UserController;
import com.joysistvi.recordingapp.model.User;

import java.util.Scanner;

public class AuthView {

    private final UserController userController;
    private final Scanner scanner;

    public AuthView(
            UserController userController,
            Scanner scanner
    ) {
        this.userController = userController;
        this.scanner = scanner;
    }

    public User run() {

        int choice = -1;

        do {
            printMenu();

            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(
                        scanner.nextLine().trim()
                );

                switch (choice) {

                    case 1:
                        LoginView loginView =
                                new LoginView(
                                        userController,
                                        scanner
                                );

                        User user = loginView.login();

                        if (user != null) {
                            return user;
                        }
                        break;

                    case 2:
                        RegisterView registerView =
                                new RegisterView(
                                        userController,
                                        scanner
                                );

                        registerView.register();
                        break;

                    case 0:
                        System.out.println("Exiting application...");
                        return null;

                    default:
                        System.out.println(
                                "Invalid option. Please try again."
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. Please enter a number."
                );

                choice = -1;
            }

        } while (choice != 0);

        return null;
    }

    private void printMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             RECORDING APP");
        System.out.println("========================================");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");
        System.out.println("========================================");
    }
}