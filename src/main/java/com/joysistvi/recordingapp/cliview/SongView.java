package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.model.Song;

import java.util.List;
import java.util.Scanner;

public class SongView {

    private final SongController songController;
    private final AlbumController albumController;
    private final Scanner scanner;

    public SongView(
            SongController songController,
            AlbumController albumController,
            Scanner scanner
    ) {
        this.songController = songController;
        this.albumController = albumController;
        this.scanner = scanner;
    }

    public void run() {
        int choice = -1;

        do {
            printMenu();
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> viewAllSongs();
                case 2 -> searchSong();
                case 3 -> addSong();
                case 4 -> updateSong();
                case 5 -> deleteSong();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }

        } while (choice != 0);
    }

    private void printMenu() {
        System.out.println("\n----- Song Management -----");
        System.out.println("1. View All Songs");
        System.out.println("2. Search Song");
        System.out.println("3. Add Song");
        System.out.println("4. Update Song");
        System.out.println("5. Delete Song");
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

    private void displaySongs(List<Song> songs) {
        if (songs == null || songs.isEmpty()) {
            System.out.println("No songs found.");
            return;
        }

        String border =
                "+------+---------------------------+------------+----------------+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-25s | %-10s | %-14s |%n",
                "ID",
                "Title",
                "Genre",
                "Album"
        );

        System.out.println(border);

        for (Song song : songs) {
            String albumName = "Unknown";

            Album album = albumController.handleGetAlbumById(
                    song.getAlbumId()
            );

            if (album != null) {
                albumName = album.getName();
            }

            System.out.printf(
                    "| %-4d | %-25s | %-10s | %-14s |%n",
                    song.getId(),
                    song.getTitle(),
                    song.getGenre(),
                    albumName
            );
        }

        System.out.println(border);
    }

    private void displayAlbums() {
        List<Album> albums = albumController.handleViewAllAlbums();

        if (albums == null || albums.isEmpty()) {
            System.out.println("No albums found.");
            return;
        }

        String border =
                "+------+------------------------------+";

        System.out.println("\n----- Available Albums -----");
        System.out.println(border);

        System.out.printf(
                "| %-4s | %-28s |%n",
                "ID",
                "Album"
        );

        System.out.println(border);

        for (Album album : albums) {
            System.out.printf(
                    "| %-4d | %-28s |%n",
                    album.getId(),
                    album.getName()
            );
        }

        System.out.println(border);
    }

    private boolean albumExists(int albumId) {
        if (albumId <= 0) {
            return false;
        }

        Album album = albumController.handleGetAlbumById(albumId);

        return album != null;
    }

    private void viewAllSongs() {
        System.out.println("\n--- All Songs ---");

        List<Song> songs = songController.handleViewAllSongs();

        displaySongs(songs);
    }

    private void searchSong() {
        System.out.println("\n----- Search Song -----");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Title / Keyword");

        int choice = readInt("Enter search option: ");

        if (choice == 1) {
            int id = readInt("Enter Song ID: ");

            Song song = songController.handleGetSongById(id);

            if (song != null) {
                displaySongs(List.of(song));
            } else {
                System.out.println(
                        "No song found with ID " + id
                );
            }

        } else if (choice == 2) {
            System.out.print("Enter title / keyword: ");
            String keyword = scanner.nextLine();

            List<Song> results =
                    songController.handleSearchSong(keyword);

            displaySongs(results);

        } else {
            System.out.println("Invalid search option.");
        }
    }

    private void addSong() {
        System.out.println("\n----- Add Song -----");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        displayAlbums();

        int albumId = readInt("Album ID: ");

        if (!albumExists(albumId)) {
            System.out.println(
                    "Invalid Album ID. Album does not exist."
            );
            return;
        }

        Song song = new Song(
                title,
                genre,
                albumId
        );

        boolean isSuccess =
                songController.handleCreateSong(song);

        System.out.println(
                isSuccess
                        ? "Song added successfully."
                        : "Failed to add song."
        );

        if (isSuccess) {
            System.out.println();
            viewAllSongs();
        }
    }

    private void updateSong() {
        System.out.println("\n----- Update Song -----");

        viewAllSongs();

        int id = readInt("Song ID to update: ");

        Song current = songController.handleGetSongById(id);

        if (current == null) {
            System.out.println(
                    "No song found with ID " + id +
                            ". Please check the ID and try again."
            );

            return;
        }

        System.out.println(
                "New Title [" +
                        current.getTitle() +
                        "] (press Enter to keep current): "
        );

        String title = scanner.nextLine();

        if (title.trim().isEmpty()) {
            title = current.getTitle();
        }

        System.out.println(
                "New Genre [" +
                        current.getGenre() +
                        "] (press Enter to keep current): "
        );

        String genre = scanner.nextLine();

        if (genre.trim().isEmpty()) {
            genre = current.getGenre();
        }

        displayAlbums();

        System.out.println(
                "New Album ID [" +
                        current.getAlbumId() +
                        "] (enter 0 to keep current): "
        );

        int albumId = readInt("");

        if (albumId == 0) {
            albumId = current.getAlbumId();
        }

        if (!albumExists(albumId)) {
            System.out.println(
                    "Invalid Album ID. Album does not exist."
            );
            return;
        }

        Song song = new Song(
                id,
                title,
                genre,
                albumId
        );

        boolean isSuccess =
                songController.handleUpdateSong(song);

        System.out.println(
                isSuccess
                        ? "Song updated successfully."
                        : "Failed to update song."
        );

        if (isSuccess) {
            System.out.println();
            viewAllSongs();
        }
    }

    private void deleteSong() {
        System.out.println("\n----- Delete Song -----");

        viewAllSongs();

        int id = readInt(
                "Song ID to delete permanently: "
        );

        boolean isSuccess =
                songController.handleDeleteSong(id);

        System.out.println(
                isSuccess
                        ? "Song deleted successfully."
                        : "Failed to delete song."
        );

        if (isSuccess) {
            System.out.println();
            viewAllSongs();
        }
    }
}