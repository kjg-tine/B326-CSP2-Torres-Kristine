package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.cliview.AlbumView;
import com.joysistvi.recordingapp.cliview.ArtistView;
import com.joysistvi.recordingapp.cliview.SongView;
import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.repository.AlbumRepo;
import com.joysistvi.recordingapp.repository.AlbumRepoImpl;
import com.joysistvi.recordingapp.repository.ArtistRepo;
import com.joysistvi.recordingapp.repository.ArtistRepoImpl;
import com.joysistvi.recordingapp.repository.SongRepo;
import com.joysistvi.recordingapp.repository.SongRepoImpl;
import com.joysistvi.recordingapp.service.AlbumService;
import com.joysistvi.recordingapp.service.AlbumServiceImpl;
import com.joysistvi.recordingapp.service.ArtistService;
import com.joysistvi.recordingapp.service.ArtistServiceImpl;
import com.joysistvi.recordingapp.service.SongService;
import com.joysistvi.recordingapp.service.SongServiceImpl;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        DbConnection dbConnection = new DbConnection();

        // Artist
        ArtistRepo artistRepo = new ArtistRepoImpl(dbConnection);
        ArtistService artistService = new ArtistServiceImpl(artistRepo);
        ArtistController artistController = new ArtistController(artistService);

        // Song
        SongRepo songRepo = new SongRepoImpl(dbConnection);
        SongService songService = new SongServiceImpl(songRepo);
        SongController songController = new SongController(songService);

        // Album
        AlbumRepo albumRepo = new AlbumRepoImpl(dbConnection);
        AlbumService albumService = new AlbumServiceImpl(albumRepo);
        AlbumController albumController = new AlbumController(albumService);

        Scanner scanner = new Scanner(System.in);

        ArtistView artistView = new ArtistView(
                artistController,
                scanner
        );

        SongView songView = new SongView(
                songController,
                scanner
        );

        AlbumView albumView = new AlbumView(
                albumController,
                scanner
        );

        // Temporary test
        albumView.run();
    }
}