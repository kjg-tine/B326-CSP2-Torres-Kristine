package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.UserController;
import com.joysistvi.recordingapp.model.User;

import java.util.List;
import java.util.Scanner;

public class UserManagementView {

    private final UserController userController;
    private final Scanner scanner;
    private final User loggedInUser;

    public UserManagementView(
            UserController userController,
            Scanner scanner,
            User loggedInUser
    ) {
        this.userController = userController;
        this.scanner = scanner;
        this.loggedInUser = loggedInUser;
    }

    public void run() {

        int choice = -1;

        do {

            printMenu();

            choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    viewAllUsers();
                    break;

                case 2:
                    addUser();
                    break;

                case 3:
                    updateUserRole();
                    break;

                case 4:
                    deleteUser();
                    break;

                case 0:
                    System.out.println(
                            "Returning to admin dashboard..."
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please try again."
                    );
            }

        } while (choice != 0);
    }

    private void printMenu() {

        System.out.println();
        System.out.println(
                "========================================"
        );
        System.out.println(
                "            USER MANAGEMENT"
        );
        System.out.println(
                "========================================"
        );
        System.out.println(
                "1. View All Users"
        );
        System.out.println(
                "2. Add User"
        );
        System.out.println(
                "3. Update User Role"
        );
        System.out.println(
                "4. Delete User"
        );
        System.out.println(
                "0. Back"
        );
        System.out.println(
                "========================================"
        );
    }

    // ========================================
    // VIEW ALL USERS
    // ========================================

    private void viewAllUsers() {

        System.out.println();
        System.out.println(
                "----------- ALL USERS -----------"
        );

        List<User> users =
                userController.handleViewAllUsers();

        displayUsers(users);
    }

    private void displayUsers(
            List<User> users
    ) {

        if (users == null ||
                users.isEmpty()) {

            System.out.println(
                    "No users found."
            );

            return;
        }

        String border =
                "+------+----------------------+----------+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-20s | %-8s |%n",
                "ID",
                "Username",
                "Role"
        );

        System.out.println(border);

        for (User user : users) {

            System.out.printf(
                    "| %-4d | %-20s | %-8s |%n",
                    user.getId(),
                    user.getUsername(),
                    user.getRole()
            );
        }

        System.out.println(border);
    }

    // ========================================
    // ADD USER
    // ========================================

    private void addUser() {

        System.out.println();
        System.out.println(
                "----------- ADD USER -----------"
        );

        System.out.print("Username: ");

        String username =
                scanner.nextLine().trim();

        if (username.isEmpty()) {

            System.out.println(
                    "Username cannot be empty."
            );

            return;
        }

        System.out.print("Password: ");

        String password =
                scanner.nextLine().trim();

        if (password.isEmpty()) {

            System.out.println(
                    "Password cannot be empty."
            );

            return;
        }

        boolean success =
                userController.handleRegister(
                        username,
                        password
                );

        if (success) {

            System.out.println(
                    "User registered successfully!"
            );

            System.out.println();

            viewAllUsers();

        } else {

            System.out.println(
                    "Failed to register user."
            );
        }
    }

    // ========================================
    // UPDATE USER ROLE
    // ========================================

    private void updateUserRole() {

        System.out.println();
        System.out.println(
                "----------- UPDATE USER ROLE -----------"
        );

        viewAllUsers();

        int userId =
                readInt("Enter User ID: ");

        User user =
                userController.handleGetUserById(
                        userId
                );

        if (user == null) {

            System.out.println(
                    "User not found."
            );

            return;
        }

        System.out.println(
                "Username: "
                        + user.getUsername()
        );

        System.out.println(
                "Current Role: "
                        + user.getRole()
        );

        System.out.println();
        System.out.println(
                "Available roles:"
        );
        System.out.println(
                "1. user"
        );
        System.out.println(
                "2. admin"
        );

        int roleChoice =
                readInt("Enter new role: ");

        String newRole;

        if (roleChoice == 1) {

            newRole = "user";

        } else if (roleChoice == 2) {

            newRole = "admin";

        } else {

            System.out.println(
                    "Invalid role option."
            );

            return;
        }

        boolean success =
                userController.handleUpdateUserRole(
                        userId,
                        newRole
                );

        if (success) {

            System.out.println(
                    "User role updated successfully!"
            );

            System.out.println(
                    "New Role: "
                            + newRole
            );

        } else {

            System.out.println(
                    "Failed to update user role."
            );
        }
    }

    // ========================================
    // DELETE USER
    // ========================================

    private void deleteUser() {

        System.out.println();
        System.out.println(
                "----------- DELETE USER -----------"
        );

        viewAllUsers();

        int userId =
                readInt("Enter User ID: ");

        // Prevent admin from deleting their own account
        if (userId == loggedInUser.getId()) {

            System.out.println(
                    "You cannot delete your own account."
            );

            return;
        }

        User user =
                userController.handleGetUserById(
                        userId
                );

        if (user == null) {

            System.out.println(
                    "User not found."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "Selected User:"
        );

        System.out.println(
                "ID: "
                        + user.getId()
        );

        System.out.println(
                "Username: "
                        + user.getUsername()
        );

        System.out.println(
                "Role: "
                        + user.getRole()
        );

        System.out.print(
                "Are you sure you want to delete this user? (Y/N): "
        );

        String confirmation =
                scanner.nextLine()
                        .trim()
                        .toLowerCase();

        if (!confirmation.equals("y")) {

            System.out.println(
                    "Delete cancelled."
            );

            return;
        }

        boolean success =
                userController.handleDeleteUser(
                        userId
                );

        if (success) {

            System.out.println(
                    "User deleted successfully!"
            );

        } else {

            System.out.println(
                    "Failed to delete user."
            );
        }
    }

    // ========================================
    // READ INTEGER
    // ========================================

    private int readInt(String prompt) {

        System.out.print(prompt);

        while (true) {

            try {

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.print(
                        "Invalid input. Please enter a number: "
                );
            }
        }
    }
}