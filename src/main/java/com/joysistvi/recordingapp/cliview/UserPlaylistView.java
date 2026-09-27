package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.PlaylistController;
import com.joysistvi.recordingapp.controller.PlaylistSongController;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.PlaylistSong;
import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.model.User;

import java.util.List;
import java.util.Scanner;

public class UserPlaylistView {

    private final Scanner scanner;
    private final User loggedInUser;

    private final PlaylistController playlistController;
    private final PlaylistSongController playlistSongController;
    private final SongController songController;

    public UserPlaylistView(
            Scanner scanner,
            User loggedInUser,
            PlaylistController playlistController,
            PlaylistSongController playlistSongController,
            SongController songController
    ) {
        this.scanner = scanner;
        this.loggedInUser = loggedInUser;
        this.playlistController = playlistController;
        this.playlistSongController = playlistSongController;
        this.songController = songController;
    }

    public void run() {

        int choice = -1;

        do {

            printMenu();

            choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    viewMyPlaylists();
                    break;

                case 2:
                    createPlaylist();
                    break;

                case 3:
                    viewSongsInPlaylist();
                    break;

                case 4:
                    addSongToPlaylist();
                    break;

                case 5:
                    removeSongFromPlaylist();
                    break;

                case 6:
                    deletePlaylist();
                    break;

                case 0:
                    System.out.println(
                            "Returning to previous menu..."
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
                "             MY PLAYLISTS"
        );
        System.out.println(
                "========================================"
        );
        System.out.println(
                "1. View My Playlists"
        );
        System.out.println(
                "2. Create Playlist"
        );
        System.out.println(
                "3. View Songs in Playlist"
        );
        System.out.println(
                "4. Add Song to Playlist"
        );
        System.out.println(
                "5. Remove Song from Playlist"
        );
        System.out.println(
                "6. Delete Playlist"
        );
        System.out.println(
                "0. Back"
        );
        System.out.println(
                "========================================"
        );
    }

    private void viewMyPlaylists() {

        System.out.println();
        System.out.println(
                "----------- MY PLAYLISTS -----------"
        );

        List<Playlist> playlists =
                playlistController.handleGetPlaylistsByUserId(
                        loggedInUser.getId()
                );

        if (playlists == null ||
                playlists.isEmpty()) {

            System.out.println(
                    "You don't have any playlists."
            );

            return;
        }

        String border =
                "+------+----------------------+----------+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-20s | %-8s |%n",
                "ID",
                "Date Created",
                "User ID"
        );

        System.out.println(border);

        for (Playlist playlist : playlists) {

            System.out.printf(
                    "| %-4d | %-20s | %-8d |%n",
                    playlist.getId(),
                    playlist.getDateCreated(),
                    playlist.getUserId()
            );
        }

        System.out.println(border);
    }

    private void createPlaylist() {

        System.out.println();
        System.out.println(
                "----------- CREATE PLAYLIST -----------"
        );

        System.out.print(
                "Date Created: "
        );

        String dateCreated =
                scanner.nextLine().trim();

        if (dateCreated.isEmpty()) {

            System.out.println(
                    "Date created cannot be empty."
            );

            return;
        }

        Playlist playlist =
                new Playlist(
                        dateCreated,
                        loggedInUser.getId()
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

    private void viewSongsInPlaylist() {

        System.out.println();
        System.out.println(
                "----------- VIEW SONGS IN PLAYLIST -----------"
        );

        int playlistId =
                readInt("Playlist ID: ");

        if (!isMyPlaylist(playlistId)) {

            System.out.println(
                    "Playlist not found."
            );

            return;
        }

        List<PlaylistSong> playlistSongs =
                playlistSongController
                        .handleGetSongsByPlaylistId(
                                playlistId
                        );

        displayPlaylistSongs(
                playlistSongs
        );
    }

    private void addSongToPlaylist() {

        System.out.println();
        System.out.println(
                "----------- ADD SONG TO PLAYLIST -----------"
        );

        int playlistId =
                readInt("Playlist ID: ");

        if (!isMyPlaylist(playlistId)) {

            System.out.println(
                    "Playlist not found."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "----------- AVAILABLE SONGS -----------"
        );

        List<Song> songs =
                songController.handleViewAllSongs();

        if (songs == null ||
                songs.isEmpty()) {

            System.out.println(
                    "No songs available."
            );

            return;
        }

        String border =
                "+------+------------------------------+";

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

        int songId =
                readInt("Song ID: ");

        Song song =
                songController.handleGetSongById(
                        songId
                );

        if (song == null) {

            System.out.println(
                    "Invalid Song ID. Song does not exist."
            );

            return;
        }

        boolean success =
                playlistSongController
                        .handleAddSongToPlaylist(
                                playlistId,
                                songId,
                                loggedInUser.getId()
                        );

        if (success) {

            System.out.println(
                    "Song added to playlist successfully!"
            );

        } else {

            System.out.println(
                    "Failed to add song to playlist."
            );
        }
    }

    private void removeSongFromPlaylist() {

        System.out.println();
        System.out.println(
                "----------- REMOVE SONG FROM PLAYLIST -----------"
        );

        int playlistId =
                readInt("Playlist ID: ");

        if (!isMyPlaylist(playlistId)) {

            System.out.println(
                    "Playlist not found."
            );

            return;
        }

        List<PlaylistSong> playlistSongs =
                playlistSongController
                        .handleGetSongsByPlaylistId(
                                playlistId
                        );

        if (playlistSongs == null ||
                playlistSongs.isEmpty()) {

            System.out.println(
                    "No songs found in this playlist."
            );

            return;
        }

        displayPlaylistSongs(
                playlistSongs
        );

        int songId =
                readInt("Song ID to remove: ");

        Song song =
                songController.handleGetSongById(
                        songId
                );

        if (song == null) {

            System.out.println(
                    "Invalid Song ID."
            );

            return;
        }

        boolean success =
                playlistSongController
                        .handleRemoveSongFromPlaylist(
                                playlistId,
                                songId,
                                loggedInUser.getId()
                        );

        if (success) {

            System.out.println(
                    "Song removed from playlist successfully!"
            );

        } else {

            System.out.println(
                    "Failed to remove song from playlist."
            );
        }
    }

    private void deletePlaylist() {

        System.out.println();
        System.out.println(
                "----------- DELETE PLAYLIST -----------"
        );

        int playlistId =
                readInt("Playlist ID: ");

        if (!isMyPlaylist(playlistId)) {

            System.out.println(
                    "Playlist not found."
            );

            return;
        }

        System.out.print(
                "Are you sure you want to delete this playlist? (Y/N): "
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
                playlistController.handleDeletePlaylist(
                        playlistId,
                        loggedInUser.getId()
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

    private boolean isMyPlaylist(
            int playlistId
    ) {

        List<Playlist> playlists =
                playlistController
                        .handleGetPlaylistsByUserId(
                                loggedInUser.getId()
                        );

        if (playlists == null) {
            return false;
        }

        for (Playlist playlist : playlists) {

            if (playlist.getId() == playlistId) {
                return true;
            }
        }

        return false;
    }

    private void displayPlaylistSongs(
            List<PlaylistSong> playlistSongs
    ) {

        if (playlistSongs == null ||
                playlistSongs.isEmpty()) {

            System.out.println(
                    "No songs found in this playlist."
            );

            return;
        }

        String border =
                "+------+------------------------------+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-28s |%n",
                "ID",
                "Song"
        );

        System.out.println(border);

        for (PlaylistSong playlistSong :
                playlistSongs) {

            String songTitle = "Unknown";

            Song song =
                    songController.handleGetSongById(
                            playlistSong.getSongId()
                    );

            if (song != null) {

                songTitle =
                        song.getTitle();
            }

            System.out.printf(
                    "| %-4d | %-28s |%n",
                    playlistSong.getSongId(),
                    songTitle
            );
        }

        System.out.println(border);
    }

    private int readInt(
            String prompt
    ) {

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