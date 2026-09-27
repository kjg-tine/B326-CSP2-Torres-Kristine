package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.cliview.AdminView;
import com.joysistvi.recordingapp.cliview.AlbumView;
import com.joysistvi.recordingapp.cliview.ArtistView;
import com.joysistvi.recordingapp.cliview.AuthView;
import com.joysistvi.recordingapp.cliview.PlaylistSongView;
import com.joysistvi.recordingapp.cliview.PlaylistView;
import com.joysistvi.recordingapp.cliview.SongView;
import com.joysistvi.recordingapp.cliview.UserManagementView;
import com.joysistvi.recordingapp.cliview.UserView;

import com.joysistvi.recordingapp.config.DbConnection;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.controller.PlaylistController;
import com.joysistvi.recordingapp.controller.PlaylistSongController;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.controller.UserController;

import com.joysistvi.recordingapp.repository.AlbumRepoImpl;
import com.joysistvi.recordingapp.repository.ArtistRepoImpl;
import com.joysistvi.recordingapp.repository.PlaylistRepoImpl;
import com.joysistvi.recordingapp.repository.PlaylistSongRepoImpl;
import com.joysistvi.recordingapp.repository.SongRepoImpl;
import com.joysistvi.recordingapp.repository.UserRepoImpl;

import com.joysistvi.recordingapp.service.AlbumServiceImpl;
import com.joysistvi.recordingapp.service.ArtistServiceImpl;
import com.joysistvi.recordingapp.service.PlaylistServiceImpl;
import com.joysistvi.recordingapp.service.PlaylistSongServiceImpl;
import com.joysistvi.recordingapp.service.SongServiceImpl;
import com.joysistvi.recordingapp.service.UserServiceImpl;

import com.joysistvi.recordingapp.model.User;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DbConnection dbConnection =
                new DbConnection();

        // ========================================
        // REPOSITORIES
        // ========================================

        ArtistRepoImpl artistRepo =
                new ArtistRepoImpl(dbConnection);

        AlbumRepoImpl albumRepo =
                new AlbumRepoImpl(dbConnection);

        SongRepoImpl songRepo =
                new SongRepoImpl(dbConnection);

        PlaylistRepoImpl playlistRepo =
                new PlaylistRepoImpl(dbConnection);

        PlaylistSongRepoImpl playlistSongRepo =
                new PlaylistSongRepoImpl(dbConnection);

        UserRepoImpl userRepo =
                new UserRepoImpl(dbConnection);

        // ========================================
        // SERVICES
        // ========================================

        ArtistServiceImpl artistService =
                new ArtistServiceImpl(artistRepo);

        AlbumServiceImpl albumService =
                new AlbumServiceImpl(albumRepo);

        SongServiceImpl songService =
                new SongServiceImpl(songRepo);

        PlaylistServiceImpl playlistService =
                new PlaylistServiceImpl(playlistRepo);

        PlaylistSongServiceImpl playlistSongService =
                new PlaylistSongServiceImpl(
                        playlistSongRepo,
                        playlistRepo
                );

        UserServiceImpl userService =
                new UserServiceImpl(userRepo);

        // ========================================
        // CONTROLLERS
        // ========================================

        ArtistController artistController =
                new ArtistController(artistService);

        AlbumController albumController =
                new AlbumController(albumService);

        SongController songController =
                new SongController(songService);

        PlaylistController playlistController =
                new PlaylistController(playlistService);

        PlaylistSongController playlistSongController =
                new PlaylistSongController(
                        playlistSongService
                );

        UserController userController =
                new UserController(userService);

        // ========================================
        // VIEWS
        // ========================================

        ArtistView artistView =
                new ArtistView(
                        artistController,
                        scanner
                );

        AlbumView albumView =
                new AlbumView(
                        albumController,
                        scanner
                );

        SongView songView =
                new SongView(
                        songController,
                        albumController,
                        scanner
                );

        PlaylistSongView playlistSongView =
                new PlaylistSongView(
                        playlistSongController,
                        songController,
                        scanner
                );

        PlaylistView playlistView =
                new PlaylistView(
                        playlistController,
                        playlistSongController,
                        songController,
                        userController,
                        scanner
                );

        AuthView authView =
                new AuthView(
                        userController,
                        scanner
                );

        AdminView adminView = null;

        // ========================================
        // APPLICATION LOOP
        // ========================================

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println(
                    "========================================"
            );
            System.out.println(
                    "       STUDIO RECORDING APP"
            );
            System.out.println(
                    "========================================"
            );

            User loggedInUser =
                    authView.run();

            if (loggedInUser == null) {

                System.out.println();
                System.out.println(
                        "Thank you for using "
                                + "Studio Recording App!"
                );

                running = false;
                break;
            }

            // ========================================
            // ADMIN
            // ========================================

            if (loggedInUser.getRole()
                    .equalsIgnoreCase("admin")) {

                UserManagementView userManagementView =
                        new UserManagementView(
                                userController,
                                scanner,
                                loggedInUser
                        );

                adminView =
                        new AdminView(
                                scanner,
                                artistView,
                                albumView,
                                songView,
                                playlistView,
                                playlistSongView,
                                userManagementView
                        );

                adminView.run();

            }

            // ========================================
            // NORMAL USER
            // ========================================

            else {

                UserView userView =
                        new UserView(
                                scanner,
                                loggedInUser,
                                artistController,
                                albumController,
                                songController,
                                playlistController,
                                playlistSongController
                        );

                userView.run();
            }
        }

        scanner.close();
    }
}