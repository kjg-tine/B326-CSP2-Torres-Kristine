package com.joysistvi.recordingapp.cliview;

import java.util.Scanner;

public class AdminView {

    private final Scanner scanner;

    private final ArtistView artistView;
    private final AlbumView albumView;
    private final SongView songView;
    private final PlaylistView playlistView;
    private final PlaylistSongView playlistSongView;
    private final UserManagementView userManagementView;

    public AdminView(
            Scanner scanner,
            ArtistView artistView,
            AlbumView albumView,
            SongView songView,
            PlaylistView playlistView,
            PlaylistSongView playlistSongView,
            UserManagementView userManagementView
    ) {
        this.scanner = scanner;
        this.artistView = artistView;
        this.albumView = albumView;
        this.songView = songView;
        this.playlistView = playlistView;
        this.playlistSongView = playlistSongView;
        this.userManagementView = userManagementView;
    }

    public void run() {

        int choice = -1;

        do {

            printMenu();

            choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    artistView.run();
                    break;

                case 2:
                    albumView.run();
                    break;

                case 3:
                    songView.run();
                    break;

                case 4:
                    playlistView.run();
                    break;

                case 5:
                    playlistSongView.run();
                    break;

                case 6:
                    userManagementView.run();
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
                "             ADMIN DASHBOARD"
        );
        System.out.println(
                "========================================"
        );
        System.out.println(
                "1. Manage Artists"
        );
        System.out.println(
                "2. Manage Albums"
        );
        System.out.println(
                "3. Manage Songs"
        );
        System.out.println(
                "4. Manage Playlists"
        );
        System.out.println(
                "5. Manage Playlist Songs"
        );
        System.out.println(
                "6. Manage Users"
        );
        System.out.println(
                "0. Logout"
        );
        System.out.println(
                "========================================"
        );
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