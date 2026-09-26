package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.PlaylistSongController;
import com.joysistvi.recordingapp.model.PlaylistSong;

import java.util.List;
import java.util.Scanner;

public class PlaylistSongView {

    private final PlaylistSongController playlistSongController;
    private final Scanner scanner;

    public PlaylistSongView(
            PlaylistSongController playlistSongController,
            Scanner scanner
    ) {
        this.playlistSongController = playlistSongController;
        this.scanner = scanner;
    }

    public void run() {

        int choice = -1;

        do {
            printMenu();
            choice = readInt("Enter choice: ");

            switch (choice) {

                case 1 -> viewAllPlaylistSongs();

                case 2 -> viewSongsInPlaylist();

                case 3 -> addSongToPlaylist();

                case 4 -> removeSongFromPlaylist();

                case 0 ->
                        System.out.println("Returning to playlist menu...");

                default ->
                        System.out.println(
                                "Invalid option. Please try again."
                        );
            }

        } while (choice != 0);
    }

    private void printMenu() {

        System.out.println("\n----- Manage Playlist Songs -----");
        System.out.println("1. View All Playlist Songs");
        System.out.println("2. View Songs in Playlist");
        System.out.println("3. Add Song to Playlist");
        System.out.println("4. Remove Song from Playlist");
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

    private void displayPlaylistSongs(
            List<PlaylistSong> playlistSongs
    ) {

        if (playlistSongs == null || playlistSongs.isEmpty()) {

            System.out.println("No playlist songs found.");
            return;
        }

        String border =
                "+------+-------------+----------+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-11s | %-8s |%n",
                "ID",
                "Playlist ID",
                "Song ID"
        );

        System.out.println(border);

        for (PlaylistSong playlistSong : playlistSongs) {

            System.out.printf(
                    "| %-4d | %-11d | %-8d |%n",
                    playlistSong.getId(),
                    playlistSong.getPlaylistId(),
                    playlistSong.getSongId()
            );
        }

        System.out.println(border);
    }

    private void viewAllPlaylistSongs() {

        System.out.println("\n--- All Playlist Songs ---");

        List<PlaylistSong> playlistSongs =
                playlistSongController
                        .handleViewAllPlaylistSongs();

        displayPlaylistSongs(playlistSongs);
    }

    private void viewSongsInPlaylist() {

        System.out.println("\n--- View Songs in Playlist ---");

        int playlistId =
                readInt("Enter Playlist ID: ");

        List<PlaylistSong> playlistSongs =
                playlistSongController
                        .handleGetSongsByPlaylistId(playlistId);

        displayPlaylistSongs(playlistSongs);
    }

    private void addSongToPlaylist() {

        System.out.println("\n--- Add Song to Playlist ---");

        int playlistId =
                readInt("Enter Playlist ID: ");

        int songId =
                readInt("Enter Song ID: ");

        boolean isSuccess =
                playlistSongController
                        .handleAddSongToPlaylist(
                                playlistId,
                                songId
                        );

        System.out.println(
                isSuccess
                        ? "Song added to playlist successfully."
                        : "Failed to add song to playlist."
        );

        if (isSuccess) {

            System.out.println();

            List<PlaylistSong> playlistSongs =
                    playlistSongController
                            .handleGetSongsByPlaylistId(
                                    playlistId
                            );

            displayPlaylistSongs(playlistSongs);
        }
    }

    private void removeSongFromPlaylist() {

        System.out.println("\n--- Remove Song from Playlist ---");

        int playlistId =
                readInt("Enter Playlist ID: ");

        int songId =
                readInt("Enter Song ID: ");

        boolean isSuccess =
                playlistSongController
                        .handleRemoveSongFromPlaylist(
                                playlistId,
                                songId
                        );

        System.out.println(
                isSuccess
                        ? "Song removed from playlist successfully."
                        : "Failed to remove song from playlist."
        );

        if (isSuccess) {

            System.out.println();

            List<PlaylistSong> playlistSongs =
                    playlistSongController
                            .handleGetSongsByPlaylistId(
                                    playlistId
                            );

            displayPlaylistSongs(playlistSongs);
        }
    }
}