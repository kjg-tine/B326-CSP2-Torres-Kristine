package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.cliview.AlbumView;
import com.joysistvi.recordingapp.cliview.ArtistView;
import com.joysistvi.recordingapp.cliview.PlaylistView;
import com.joysistvi.recordingapp.cliview.SongView;

import com.joysistvi.recordingapp.config.DbConnection;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.controller.PlaylistController;
import com.joysistvi.recordingapp.controller.PlaylistSongController;
import com.joysistvi.recordingapp.controller.SongController;

import com.joysistvi.recordingapp.repository.AlbumRepo;
import com.joysistvi.recordingapp.repository.AlbumRepoImpl;
import com.joysistvi.recordingapp.repository.ArtistRepo;
import com.joysistvi.recordingapp.repository.ArtistRepoImpl;
import com.joysistvi.recordingapp.repository.PlaylistRepo;
import com.joysistvi.recordingapp.repository.PlaylistRepoImpl;
import com.joysistvi.recordingapp.repository.PlaylistSongRepo;
import com.joysistvi.recordingapp.repository.PlaylistSongRepoImpl;
import com.joysistvi.recordingapp.repository.SongRepo;
import com.joysistvi.recordingapp.repository.SongRepoImpl;

import com.joysistvi.recordingapp.service.AlbumService;
import com.joysistvi.recordingapp.service.AlbumServiceImpl;
import com.joysistvi.recordingapp.service.ArtistService;
import com.joysistvi.recordingapp.service.ArtistServiceImpl;
import com.joysistvi.recordingapp.service.PlaylistService;
import com.joysistvi.recordingapp.service.PlaylistServiceImpl;
import com.joysistvi.recordingapp.service.PlaylistSongService;
import com.joysistvi.recordingapp.service.PlaylistSongServiceImpl;
import com.joysistvi.recordingapp.service.SongService;
import com.joysistvi.recordingapp.service.SongServiceImpl;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        DbConnection dbConnection = new DbConnection();

        Scanner scanner = new Scanner(System.in);


        // =========================
        // ARTIST
        // =========================

        ArtistRepo artistRepo =
                new ArtistRepoImpl(dbConnection);

        ArtistService artistService =
                new ArtistServiceImpl(artistRepo);

        ArtistController artistController =
                new ArtistController(artistService);

        ArtistView artistView =
                new ArtistView(
                        artistController,
                        scanner
                );


        // =========================
        // SONG
        // =========================

        SongRepo songRepo =
                new SongRepoImpl(dbConnection);

        SongService songService =
                new SongServiceImpl(songRepo);

        SongController songController =
                new SongController(songService);

        SongView songView =
                new SongView(
                        songController,
                        scanner
                );


        // =========================
        // ALBUM
        // =========================

        AlbumRepo albumRepo =
                new AlbumRepoImpl(dbConnection);

        AlbumService albumService =
                new AlbumServiceImpl(albumRepo);

        AlbumController albumController =
                new AlbumController(albumService);

        AlbumView albumView =
                new AlbumView(
                        albumController,
                        scanner
                );


        // =========================
        // PLAYLIST
        // =========================

        PlaylistRepo playlistRepo =
                new PlaylistRepoImpl(dbConnection);

        PlaylistService playlistService =
                new PlaylistServiceImpl(playlistRepo);

        PlaylistController playlistController =
                new PlaylistController(playlistService);


        // =========================
        // PLAYLIST SONG
        // =========================

        PlaylistSongRepo playlistSongRepo =
                new PlaylistSongRepoImpl(dbConnection);

        PlaylistSongService playlistSongService =
                new PlaylistSongServiceImpl(playlistSongRepo);

        PlaylistSongController playlistSongController =
                new PlaylistSongController(playlistSongService);


        PlaylistView playlistView =
                new PlaylistView(
                        playlistController,
                        playlistSongController,
                        scanner
                );


        // =========================
        // MAIN MENU
        // =========================

        int choice = -1;

        do {

            System.out.println("\n==============================");
            System.out.println("       RECORDING APP");
            System.out.println("==============================");
            System.out.println("1. Artist");
            System.out.println("2. Album");
            System.out.println("3. Song");
            System.out.println("4. Playlist");
            System.out.println("0. Exit");
            System.out.println("==============================");

            System.out.print("Enter choice: ");

            try {

                choice = Integer.parseInt(
                        scanner.nextLine().trim()
                );

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

                    case 0:
                        System.out.println(
                                "Thank you for using Recording App!"
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

        scanner.close();
    }
}