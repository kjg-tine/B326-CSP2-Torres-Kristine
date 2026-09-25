package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.model.Album;

import java.util.List;
import java.util.Scanner;

public class AlbumView {

    private final AlbumController albumController;
    private final Scanner scanner;

    public AlbumView(AlbumController albumController, Scanner scanner) {
        this.albumController = albumController;
        this.scanner = scanner;
    }

    public void run() {
        int choice = -1;

        do {
            printMenu();
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> viewAllAlbums();
                case 2 -> searchAlbum();
                case 3 -> addAlbum();
                case 4 -> updateAlbum();
                case 5 -> deleteAlbum();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid option. Please try again.");
            }

        } while (choice != 0);
    }

    private void printMenu() {
        System.out.println("\n----- Album Management -----");
        System.out.println("1. View All Albums");
        System.out.println("2. Search Album");
        System.out.println("3. Add Album");
        System.out.println("4. Update Album");
        System.out.println("5. Delete Album");
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

    private void displayAlbums(List<Album> albums) {

        if (albums == null || albums.isEmpty()) {
            System.out.println("No albums found.");
            return;
        }

        String border =
                "+------+---------------------------+------------+-----------+";

        System.out.println(border);

        System.out.printf(
                "| %-4s | %-25s | %-10s | %-9s |%n",
                "ID",
                "Name",
                "Year",
                "Artist ID"
        );

        System.out.println(border);

        for (Album album : albums) {

            System.out.printf(
                    "| %-4d | %-25s | %-10s | %-9d |%n",
                    album.getId(),
                    album.getName(),
                    album.getYear(),
                    album.getArtistId()
            );
        }

        System.out.println(border);
    }

    private void viewAllAlbums() {

        System.out.println("\n--- All Albums ---");

        List<Album> albums =
                albumController.handleViewAllAlbums();

        displayAlbums(albums);
    }

    private void searchAlbum() {

        System.out.println("\n----- Search Album -----");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name / Keyword");

        int choice = readInt("Enter search option: ");

        if (choice == 1) {

            int id = readInt("Enter Album ID: ");

            Album album =
                    albumController.handleGetAlbumById(id);

            if (album != null) {
                displayAlbums(List.of(album));
            } else {
                System.out.println(
                        "No album found with ID " + id
                );
            }

        } else if (choice == 2) {

            System.out.print("Enter name / keyword: ");
            String keyword = scanner.nextLine();

            List<Album> results =
                    albumController.handleSearchAlbum(keyword);

            displayAlbums(results);

        } else {

            System.out.println("Invalid search option.");
        }
    }

    private void addAlbum() {

        System.out.println("\n----- Add Album -----");

        System.out.print("Album Name: ");
        String name = scanner.nextLine();

        System.out.print("Year (YYYY-MM-DD): ");
        String year = scanner.nextLine();

        int artistId = readInt("Artist ID: ");

        Album album =
                new Album(name, year, artistId);

        boolean isSuccess =
                albumController.handleCreateAlbum(album);

        System.out.println(
                isSuccess
                        ? "Album added successfully."
                        : "Failed to add album."
        );

        if (isSuccess) {
            System.out.println();
            viewAllAlbums();
        }
    }

    private void updateAlbum() {

        System.out.println("\n----- Update Album -----");

        viewAllAlbums();

        int id = readInt("Album ID to update: ");

        Album current =
                albumController.handleGetAlbumById(id);

        if (current == null) {

            System.out.println(
                    "No album found with ID " + id +
                            ". Please check the ID and try again."
            );

            return;
        }

        System.out.println(
                "New Name [" + current.getName() +
                        "] (press Enter to keep current): "
        );

        String name = scanner.nextLine();

        if (name.trim().isEmpty()) {
            name = current.getName();
        }

        System.out.println(
                "New Year [" + current.getYear() +
                        "] (press Enter to keep current): "
        );

        String year = scanner.nextLine();

        if (year.trim().isEmpty()) {
            year = current.getYear();
        }

        System.out.println(
                "New Artist ID [" + current.getArtistId() +
                        "] (enter 0 to keep current): "
        );

        int artistId = readInt("");

        if (artistId == 0) {
            artistId = current.getArtistId();
        }

        Album album =
                new Album(
                        id,
                        name,
                        year,
                        artistId
                );

        boolean isSuccess =
                albumController.handleUpdateAlbum(album);

        System.out.println(
                isSuccess
                        ? "Album updated successfully."
                        : "Failed to update album."
        );

        if (isSuccess) {
            System.out.println();
            viewAllAlbums();
        }
    }

    private void deleteAlbum() {

        System.out.println("\n----- Delete Album -----");

        viewAllAlbums();

        int id =
                readInt("Album ID to delete permanently: ");

        boolean isSuccess =
                albumController.handleDeleteAlbum(id);

        System.out.println(
                isSuccess
                        ? "Album deleted successfully."
                        : "Failed to delete album."
        );

        if (isSuccess) {
            System.out.println();
            viewAllAlbums();
        }
    }
}