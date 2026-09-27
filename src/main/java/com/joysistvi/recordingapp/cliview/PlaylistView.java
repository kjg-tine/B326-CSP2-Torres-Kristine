package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.PlaylistController;
import com.joysistvi.recordingapp.controller.PlaylistSongController;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.controller.UserController;
import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class PlaylistView {

    private final PlaylistController playlistController;
    private final PlaylistSongController playlistSongController;
    private final SongController songController;
    private final UserController userController;
    private final Scanner scanner;

    public PlaylistView(
            PlaylistController playlistController,
            PlaylistSongController playlistSongController,
            SongController songController,
            UserController userController,
            Scanner scanner
    ) {
        this.playlistController = playlistController;
        this.playlistSongController = playlistSongController;
        this.songController = songController;
        this.userController = userController;
        this.scanner = scanner;
    }

    public void run() {

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
                        viewAllPlaylists();
                        break;

                    case 2:
                        searchPlaylist();
                        break;

                    case 3:
                        addPlaylist();
                        break;

                    case 4:
                        updatePlaylist();
                        break;

                    case 5:
                        deletePlaylist();
                        break;

                    case 6:
                        managePlaylistSongs();
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

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. Please enter a number."
                );

                choice = -1;
            }

        } while (choice != 0);
    }

    // ========================================
    // MENU
    // ========================================

    private void printMenu() {

        System.out.println();
        System.out.println(
                "========================================"
        );
        System.out.println(
                "           MANAGE PLAYLISTS"
        );
        System.out.println(
                "========================================"
        );
        System.out.println(
                "1. View All Playlists"
        );
        System.out.println(
                "2. Search Playlist"
        );
        System.out.println(
                "3. Add Playlist"
        );
        System.out.println(
                "4. Update Playlist"
        );
        System.out.println(
                "5. Delete Playlist"
        );
        System.out.println(
                "6. Manage Playlist Songs"
        );
        System.out.println(
                "0. Back"
        );
        System.out.println(
                "========================================"
        );
    }

    // ========================================
    // VIEW ALL PLAYLISTS
    // ========================================

    private void viewAllPlaylists() {

        System.out.println();
        System.out.println(
                "----------- ALL PLAYLISTS -----------"
        );

        List<Playlist> playlists =
                playlistController.handleViewAllPlaylists();

        displayPlaylists(playlists);
    }

    // ========================================
    // SEARCH PLAYLIST
    // ========================================

    private void searchPlaylist() {

        System.out.println();
        System.out.println(
                "----------- SEARCH PLAYLIST -----------"
        );

        System.out.println(
                "Search by date created."
        );

        System.out.print(
                "Enter keyword: "
        );

        String keyword =
                scanner.nextLine().trim();

        if (keyword.isEmpty()) {

            System.out.println(
                    "Search keyword cannot be empty."
            );

            return;
        }

        List<Playlist> playlists =
                playlistController.handleSearchPlaylist(
                        keyword
                );

        displayPlaylists(playlists);
    }

    // ========================================
    // DISPLAY USERS
    // ========================================

    private void displayUsers() {

        List<User> users =
                userController.handleViewAllUsers();

        if (users == null || users.isEmpty()) {

            System.out.println(
                    "No users found."
            );

            return;
        }

        String border =
                "+------+----------------------+----------+";

        System.out.println();
        System.out.println(
                "----------- AVAILABLE USERS -----------"
        );

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
    // ADD PLAYLIST
    // ========================================

    private void addPlaylist() {

        System.out.println();
        System.out.println(
                "----------- ADD PLAYLIST -----------"
        );

        System.out.print(
                "Enter date created (YYYY-MM-DD): "
        );

        String dateCreated =
                scanner.nextLine().trim();

        if (dateCreated.isEmpty()) {

            System.out.println(
                    "Date cannot be empty."
            );

            return;
        }

        if (!isValidDate(dateCreated)) {

            System.out.println(
                    "Invalid date. Please use YYYY-MM-DD."
            );

            return;
        }

        displayUsers();

        int userId =
                readInt("Enter User ID: ");

        User user =
                userController.handleGetUserById(userId);

        if (user == null) {

            System.out.println(
                    "User not found."
            );

            return;
        }

        System.out.println(
                "Selected User: "
                        + user.getUsername()
        );

        Playlist playlist =
                new Playlist(
                        dateCreated,
                        userId
                );

        boolean success =
                playlistController.handleCreatePlaylist(
                        playlist
                );

        if (success) {

            System.out.println(
                    "Playlist created successfully!"
            );

        } else {

            System.out.println(
                    "Failed to create playlist."
            );
        }
    }

    // ========================================
    // UPDATE PLAYLIST
    // ========================================

    private void updatePlaylist() {

        System.out.println();
        System.out.println(
                "----------- UPDATE PLAYLIST -----------"
        );

        viewAllPlaylists();

        int playlistId =
                readInt("Enter Playlist ID: ");

        Playlist existingPlaylist =
                playlistController.handleGetPlaylistById(
                        playlistId
                );

        if (existingPlaylist == null) {

            System.out.println(
                    "Playlist not found."
            );

            return;
        }

        System.out.println(
                "Current Date Created: "
                        + existingPlaylist.getDateCreated()
        );

        System.out.print(
                "Enter new date created (Enter to keep current): "
        );

        String dateCreated =
                scanner.nextLine().trim();

        if (dateCreated.isEmpty()) {

            dateCreated =
                    existingPlaylist.getDateCreated();

        } else if (!isValidDate(dateCreated)) {

            System.out.println(
                    "Invalid date. Please use YYYY-MM-DD."
            );

            return;
        }

        System.out.println(
                "Current User ID: "
                        + existingPlaylist.getUserId()
        );

        User currentUser =
                userController.handleGetUserById(
                        existingPlaylist.getUserId()
                );

        if (currentUser != null) {

            System.out.println(
                    "Current Username: "
                            + currentUser.getUsername()
            );
        }

        displayUsers();

        System.out.print(
                "Enter new User ID (0 to keep current): "
        );

        int userId =
                readIntAllowZero();

        if (userId == 0) {

            userId =
                    existingPlaylist.getUserId();

        } else {

            User newUser =
                    userController.handleGetUserById(
                            userId
                    );

            if (newUser == null) {

                System.out.println(
                        "User not found."
                );

                return;
            }

            System.out.println(
                    "Selected User: "
                            + newUser.getUsername()
            );
        }

        Playlist updatedPlaylist =
                new Playlist(
                        playlistId,
                        dateCreated,
                        userId
                );

        boolean success =
                playlistController.handleUpdatePlaylist(
                        updatedPlaylist
                );

        if (success) {

            System.out.println(
                    "Playlist updated successfully!"
            );

        } else {

            System.out.println(
                    "Failed to update playlist."
            );
        }
    }

    // ========================================
    // DELETE PLAYLIST
    // ========================================

    private void deletePlaylist() {

        System.out.println();
        System.out.println(
                "----------- DELETE PLAYLIST -----------"
        );

        viewAllPlaylists();

        int playlistId =
                readInt("Enter Playlist ID: ");

        Playlist playlist =
                playlistController.handleGetPlaylistById(
                        playlistId
                );

        if (playlist == null) {

            System.out.println(
                    "Playlist not found."
            );

            return;
        }

        System.out.println(
                "Playlist ID: "
                        + playlist.getId()
        );

        System.out.println(
                "Date Created: "
                        + playlist.getDateCreated()
        );

        System.out.println(
                "User ID: "
                        + playlist.getUserId()
        );

        User user =
                userController.handleGetUserById(
                        playlist.getUserId()
                );

        if (user != null) {

            System.out.println(
                    "Username: "
                            + user.getUsername()
            );
        }

        System.out.print(
                "Are you sure you want to delete this playlist? (Y/N): "
        );

        String confirmation =
                scanner.nextLine().trim();

        if (!confirmation.equalsIgnoreCase("Y")) {

            System.out.println(
                    "Delete cancelled."
            );

            return;
        }

        boolean success =
                playlistController.handleDeletePlaylist(
                        playlistId
                );

        if (success) {

            System.out.println(
                    "Playlist deleted successfully!"
            );

        } else {

            System.out.println(
                    "Failed to delete playlist."
            );
        }
    }

    // ========================================
    // MANAGE PLAYLIST SONGS
    // ========================================

    private void managePlaylistSongs() {

        PlaylistSongView playlistSongView =
                new PlaylistSongView(
                        playlistSongController,
                        songController,
                        scanner
                );

        playlistSongView.run();
    }

    // ========================================
    // DISPLAY PLAYLISTS
    // ========================================

    private void displayPlaylists(
            List<Playlist> playlists
    ) {

        if (playlists == null ||
                playlists.isEmpty()) {

            System.out.println(
                    "No playlists found."
            );

            return;
        }

        String border =
                "+------+----------------------+-----------+----------------+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-20s | %-9s | %-14s |%n",
                "ID",
                "Date Created",
                "User ID",
                "Username"
        );

        System.out.println(border);

        for (Playlist playlist : playlists) {

            User user =
                    userController.handleGetUserById(
                            playlist.getUserId()
                    );

            String username =
                    user != null
                            ? user.getUsername()
                            : "Unknown";

            System.out.printf(
                    "| %-4d | %-20s | %-9d | %-14s |%n",
                    playlist.getId(),
                    playlist.getDateCreated(),
                    playlist.getUserId(),
                    username
            );
        }

        System.out.println(border);
    }

    // ========================================
    // VALIDATE DATE
    // ========================================

    private boolean isValidDate(String date) {

        try {

            LocalDate.parse(date);

            return true;

        } catch (DateTimeParseException e) {

            return false;
        }
    }

    // ========================================
    // READ INTEGER
    // ========================================

    private int readInt(String prompt) {

        System.out.print(prompt);

        while (true) {

            try {

                int value =
                        Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                if (value <= 0) {

                    System.out.print(
                            "Enter a number greater than 0: "
                    );

                    continue;
                }

                return value;

            } catch (NumberFormatException e) {

                System.out.print(
                        "Invalid input. Enter a valid number: "
                );
            }
        }
    }

    // ========================================
    // READ INTEGER INCLUDING ZERO
    // ========================================

    private int readIntAllowZero() {

        while (true) {

            try {

                int value =
                        Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                if (value < 0) {

                    System.out.print(
                            "Enter 0 or a positive number: "
                    );

                    continue;
                }

                return value;

            } catch (NumberFormatException e) {

                System.out.print(
                        "Invalid input. Enter a valid number: "
                );
            }
        }
    }
}