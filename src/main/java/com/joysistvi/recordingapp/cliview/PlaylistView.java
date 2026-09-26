package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.PlaylistController;
import com.joysistvi.recordingapp.controller.PlaylistSongController;
import com.joysistvi.recordingapp.model.Playlist;

import java.util.List;
import java.util.Scanner;

public class PlaylistView {

    private final PlaylistController playlistController;
    private final PlaylistSongController playlistSongController;
    private final Scanner scanner;

    public PlaylistView(
            PlaylistController playlistController,
            PlaylistSongController playlistSongController,
            Scanner scanner
    ) {
        this.playlistController = playlistController;
        this.playlistSongController = playlistSongController;
        this.scanner = scanner;
    }

    public void run() {

        int choice = -1;

        do {
            printMenu();
            choice = readInt("Enter choice: ");

            switch (choice) {

                case 1 -> viewAllPlaylists();

                case 2 -> searchPlaylist();

                case 3 -> addPlaylist();

                case 4 -> updatePlaylist();

                case 5 -> deletePlaylist();

                case 6 -> managePlaylistSongs();

                case 0 ->
                        System.out.println("Returning to main menu...");

                default ->
                        System.out.println(
                                "Invalid option. Please try again."
                        );
            }

        } while (choice != 0);
    }

    private void printMenu() {

        System.out.println("\n----- Playlist Management -----");
        System.out.println("1. View All Playlists");
        System.out.println("2. Search Playlist");
        System.out.println("3. Add Playlist");
        System.out.println("4. Update Playlist");
        System.out.println("5. Delete Playlist");
        System.out.println("6. Manage Playlist Songs");
        System.out.println("0. Back");
    }

    private int readInt(String prompt) {

        System.out.print(prompt);

        while (true) {

            try {
                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.print(
                        "Invalid input. Please enter a valid number: "
                );
            }
        }
    }

    private void displayPlaylists(List<Playlist> playlists) {

        if (playlists == null || playlists.isEmpty()) {

            System.out.println("No playlists found.");
            return;
        }

        String border =
                "+------+----------------------+-----------+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-20s | %-9s |%n",
                "ID",
                "Date Created",
                "User ID"
        );

        System.out.println(border);

        for (Playlist playlist : playlists) {

            System.out.printf(
                    "| %-4d | %-20s | %-9d |%n",
                    playlist.getId(),
                    playlist.getDateCreated(),
                    playlist.getUserId()
            );
        }

        System.out.println(border);
    }

    private void viewAllPlaylists() {

        System.out.println("\n--- All Playlists ---");

        List<Playlist> playlists =
                playlistController.handleViewAllPlaylists();

        displayPlaylists(playlists);
    }

    private void searchPlaylist() {

        System.out.println("\n----- Search Playlist -----");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Date Created / Keyword");

        int choice = readInt("Enter search option: ");

        if (choice == 1) {

            int id = readInt("Enter Playlist ID: ");

            Playlist playlist =
                    playlistController.handleGetPlaylistById(id);

            if (playlist != null) {

                displayPlaylists(List.of(playlist));

            } else {

                System.out.println(
                        "No playlist found with ID " + id
                );
            }

        } else if (choice == 2) {

            System.out.print("Enter date / keyword: ");

            String keyword = scanner.nextLine();

            List<Playlist> results =
                    playlistController.handleSearchPlaylist(keyword);

            displayPlaylists(results);

        } else {

            System.out.println("Invalid search option.");
        }
    }

    private void addPlaylist() {

        System.out.println("\n----- Add Playlist -----");

        System.out.print("Date Created: ");
        String dateCreated = scanner.nextLine();

        int userId = readInt("User ID: ");

        Playlist playlist =
                new Playlist(dateCreated, userId);

        boolean isSuccess =
                playlistController.handleCreatePlaylist(playlist);

        System.out.println(
                isSuccess
                        ? "Playlist added successfully."
                        : "Failed to add playlist."
        );

        if (isSuccess) {

            System.out.println();

            viewAllPlaylists();
        }
    }

    private void updatePlaylist() {

        System.out.println("\n----- Update Playlist -----");

        viewAllPlaylists();

        int id = readInt("Playlist ID to update: ");

        Playlist current =
                playlistController.handleGetPlaylistById(id);

        if (current == null) {

            System.out.println(
                    "No playlist found with ID " + id +
                            ". Please check the ID and try again."
            );

            return;
        }

        System.out.println(
                "New Date Created [" +
                        current.getDateCreated() +
                        "] (press Enter to keep current): "
        );

        String dateCreated = scanner.nextLine();

        if (dateCreated.trim().isEmpty()) {

            dateCreated = current.getDateCreated();
        }

        System.out.println(
                "New User ID [" +
                        current.getUserId() +
                        "] (enter 0 to keep current): "
        );

        int userId = readInt("");

        if (userId == 0) {

            userId = current.getUserId();
        }

        Playlist playlist =
                new Playlist(
                        id,
                        dateCreated,
                        userId
                );

        boolean isSuccess =
                playlistController.handleUpdatePlaylist(playlist);

        System.out.println(
                isSuccess
                        ? "Playlist updated successfully."
                        : "Failed to update playlist."
        );

        if (isSuccess) {

            System.out.println();

            viewAllPlaylists();
        }
    }

    private void deletePlaylist() {

        System.out.println("\n----- Delete Playlist -----");

        viewAllPlaylists();

        int id =
                readInt("Playlist ID to delete permanently: ");

        boolean isSuccess =
                playlistController.handleDeletePlaylist(id);

        System.out.println(
                isSuccess
                        ? "Playlist deleted successfully."
                        : "Failed to delete playlist."
        );

        if (isSuccess) {

            System.out.println();

            viewAllPlaylists();
        }
    }

    private void managePlaylistSongs() {

        PlaylistSongView playlistSongView =
                new PlaylistSongView(
                        playlistSongController,
                        scanner
                );

        playlistSongView.run();
    }
}