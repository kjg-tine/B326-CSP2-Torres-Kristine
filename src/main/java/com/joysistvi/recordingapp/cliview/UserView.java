package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.controller.PlaylistController;
import com.joysistvi.recordingapp.controller.PlaylistSongController;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.model.Artist;
import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.model.User;

import java.util.List;
import java.util.Scanner;

public class UserView {

    private final Scanner scanner;
    private final User loggedInUser;

    private final ArtistController artistController;
    private final AlbumController albumController;
    private final SongController songController;
    private final PlaylistController playlistController;
    private final PlaylistSongController playlistSongController;

    public UserView(
            Scanner scanner,
            User loggedInUser,
            ArtistController artistController,
            AlbumController albumController,
            SongController songController,
            PlaylistController playlistController,
            PlaylistSongController playlistSongController
    ) {
        this.scanner = scanner;
        this.loggedInUser = loggedInUser;
        this.artistController = artistController;
        this.albumController = albumController;
        this.songController = songController;
        this.playlistController = playlistController;
        this.playlistSongController = playlistSongController;
    }

    public void run() {

        int choice = -1;

        do {

            printMenu();

            choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    viewArtists();
                    break;

                case 2:
                    viewAlbums();
                    break;

                case 3:
                    viewSongs();
                    break;

                case 4:
                    search();
                    break;

                case 5:
                    openMyPlaylists();
                    break;

                case 0:
                    System.out.println(
                            "Logging out..."
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
                "              USER DASHBOARD"
        );
        System.out.println(
                "========================================"
        );
        System.out.println(
                "Welcome, "
                        + loggedInUser.getUsername()
        );
        System.out.println();

        System.out.println(
                "1. View Artists"
        );

        System.out.println(
                "2. View Albums"
        );

        System.out.println(
                "3. View Songs"
        );

        System.out.println(
                "4. Search"
        );

        System.out.println(
                "5. My Playlists"
        );

        System.out.println(
                "0. Logout"
        );

        System.out.println(
                "========================================"
        );
    }

    private void viewArtists() {

        System.out.println();
        System.out.println(
                "----------- ARTISTS -----------"
        );

        List<Artist> artists =
                artistController.handleViewAllArtists();

        if (artists == null ||
                artists.isEmpty()) {

            System.out.println(
                    "No artists found."
            );

            return;
        }

        String border =
                "+------+------------------------------+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-28s |%n",
                "ID",
                "Artist"
        );

        System.out.println(border);

        for (Artist artist : artists) {

            System.out.printf(
                    "| %-4d | %-28s |%n",
                    artist.getId(),
                    artist.getName()
            );
        }

        System.out.println(border);
    }

    private void viewAlbums() {

        System.out.println();
        System.out.println(
                "----------- ALBUMS -----------"
        );

        List<Album> albums =
                albumController.handleViewAllAlbums();

        if (albums == null ||
                albums.isEmpty()) {

            System.out.println(
                    "No albums found."
            );

            return;
        }

        String border =
                "+------+------------------------------+------+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-28s | %-4s |%n",
                "ID",
                "Album",
                "Year"
        );

        System.out.println(border);

        for (Album album : albums) {

            System.out.printf(
                    "| %-4d | %-28s | %-4s |%n",
                    album.getId(),
                    album.getName(),
                    album.getYear()
            );
        }

        System.out.println(border);
    }

    private void viewSongs() {

        System.out.println();
        System.out.println(
                "----------- SONGS -----------"
        );

        List<Song> songs =
                songController.handleViewAllSongs();

        if (songs == null ||
                songs.isEmpty()) {

            System.out.println(
                    "No songs found."
            );

            return;
        }

        String border =
                "+------+------------------------------+----------------+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-28s | %-14s |%n",
                "ID",
                "Song",
                "Genre"
        );

        System.out.println(border);

        for (Song song : songs) {

            System.out.printf(
                    "| %-4d | %-28s | %-14s |%n",
                    song.getId(),
                    song.getTitle(),
                    song.getGenre()
            );
        }

        System.out.println(border);
    }

    private void search() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("               SEARCH");
        System.out.println("========================================");

        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return;
        }

        System.out.println();
        System.out.println("----------- ARTISTS -----------");

        List<Artist> artists =
                artistController.handleSearchArtist(keyword);

        if (artists == null || artists.isEmpty()) {
            System.out.println("No artists found.");
        } else {

            String border =
                    "+------+------------------------------+";

            System.out.println(border);

            System.out.printf(
                    "| %-4s | %-28s |%n",
                    "ID", "Artist"
            );

            System.out.println(border);

            for (Artist artist : artists) {

                System.out.printf(
                        "| %-4d | %-28s |%n",
                        artist.getId(),
                        artist.getName()
                );
            }

            System.out.println(border);
        }

        System.out.println();
        System.out.println("----------- ALBUMS -----------");

        List<Album> albums =
                albumController.handleSearchAlbum(keyword);

        if (albums == null || albums.isEmpty()) {
            System.out.println("No albums found.");
        } else {

            String border =
                    "+------+------------------------------+----------+";

            System.out.println(border);

            System.out.printf(
                    "| %-4s | %-28s | %-8s |%n",
                    "ID", "Album", "Artist ID"
            );

            System.out.println(border);

            for (Album album : albums) {

                System.out.printf(
                        "| %-4d | %-28s | %-8d |%n",
                        album.getId(),
                        album.getName(),
                        album.getArtistId()
                );
            }

            System.out.println(border);
        }

        System.out.println();
        System.out.println("----------- SONGS -----------");

        List<Song> songs =
                songController.handleSearchSong(keyword);

        if (songs == null || songs.isEmpty()) {
            System.out.println("No songs found.");
        } else {

            String border =
                    "+------+------------------------------+----------------+----------+";

            System.out.println(border);

            System.out.printf(
                    "| %-4s | %-28s | %-14s | %-8s |%n",
                    "ID", "Title", "Genre", "Album ID"
            );

            System.out.println(border);

            for (Song song : songs) {

                System.out.printf(
                        "| %-4d | %-28s | %-14s | %-8d |%n",
                        song.getId(),
                        song.getTitle(),
                        song.getGenre(),
                        song.getAlbumId()
                );
            }

            System.out.println(border);
        }
    }

    private void openMyPlaylists() {

        UserPlaylistView userPlaylistView =
                new UserPlaylistView(
                        scanner,
                        loggedInUser,
                        playlistController,
                        playlistSongController,
                        songController
                );

        userPlaylistView.run();
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
                        "Invalid input. Please enter a number: "
                );
            }
        }
    }
}