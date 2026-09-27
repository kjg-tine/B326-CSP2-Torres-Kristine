package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.PlaylistSongController;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.model.PlaylistSong;
import com.joysistvi.recordingapp.model.Song;

import java.util.List;
import java.util.Scanner;

public class PlaylistSongView {

    private final PlaylistSongController playlistSongController;
    private final SongController songController;
    private final Scanner scanner;

    public PlaylistSongView(
            PlaylistSongController playlistSongController,
            SongController songController,
            Scanner scanner
    ) {
        this.playlistSongController = playlistSongController;
        this.songController = songController;
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
                case 0 -> System.out.println(
                        "Returning to previous menu..."
                );
                default -> System.out.println("Invalid option. Please try again.");
            }

        } while (choice != 0);
    }

    private void printMenu() {

        System.out.println("\n----- Playlist Song Management -----");
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
                return Integer.parseInt(scanner.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    private void displayPlaylistSongs(List<PlaylistSong> playlistSongs) {

        if (playlistSongs == null || playlistSongs.isEmpty()) {
            System.out.println("No songs found in this playlist.");
            return;
        }

        String border = "+------+------------------------------+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-28s |%n",
                "ID",
                "Song"
        );

        System.out.println(border);

        for (PlaylistSong playlistSong : playlistSongs) {

            String songTitle = "Unknown";

            Song song = songController.handleGetSongById(playlistSong.getSongId());

            if (song != null) {
                songTitle = song.getTitle();
            }

            System.out.printf(
                    "| %-4d | %-28s |%n",
                    playlistSong.getId(),
                    songTitle
            );
        }

        System.out.println(border);
    }

    private void viewAllPlaylistSongs() {

        System.out.println("\n----- All Playlist Songs -----");

        List<PlaylistSong> playlistSongs = playlistSongController.handleViewAllPlaylistSongs();

        displayPlaylistSongs(playlistSongs);
    }

    private void viewSongsInPlaylist() {

        System.out.println("\n----- View Songs in Playlist -----");

        int playlistId = readInt("Playlist ID: ");

        List<PlaylistSong> playlistSongs = playlistSongController.handleGetSongsByPlaylistId(playlistId);

        displayPlaylistSongs(playlistSongs);
    }

    private void addSongToPlaylist() {

        System.out.println("\n----- Add Song to Playlist -----");

        int playlistId = readInt("Playlist ID: ");

        System.out.println("\n----- Available Songs -----");

        List<Song> songs = songController.handleViewAllSongs();

        if (songs == null || songs.isEmpty()) {
            System.out.println("No songs available.");
            return;
        }

        String border = "+------+------------------------------+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-28s |%n",
                "ID",
                "Song"
        );

        System.out.println(border);

        for (Song song : songs) {

            System.out.printf(
                    "| %-4d | %-28s |%n",
                    song.getId(),
                    song.getTitle()
            );
        }

        System.out.println(border);

        int songId = readInt("Song ID: ");

        Song song = songController.handleGetSongById(songId);

        if (song == null) {
            System.out.println("Invalid Song ID. Song does not exist.");
            return;
        }

        boolean isSuccess = playlistSongController.handleAddSongToPlaylist(playlistId, songId);

        System.out.println(isSuccess ? "Song added to playlist successfully." : "Failed to add song to playlist.");
    }

    private void removeSongFromPlaylist() {

        System.out.println("\n----- Remove Song from Playlist -----");

        int playlistId = readInt("Playlist ID: ");

        List<PlaylistSong> playlistSongs = playlistSongController.handleGetSongsByPlaylistId(playlistId);

        if (playlistSongs == null || playlistSongs.isEmpty()) {
            System.out.println("No songs found in this playlist.");
            return;
        }

        displayPlaylistSongs(playlistSongs);

        int songId = readInt("Song ID to remove: ");

        boolean isSuccess = playlistSongController.handleRemoveSongFromPlaylist(playlistId, songId);

        System.out.println(isSuccess ? "Song removed from playlist successfully." : "Failed to remove song from playlist.");
    }
}