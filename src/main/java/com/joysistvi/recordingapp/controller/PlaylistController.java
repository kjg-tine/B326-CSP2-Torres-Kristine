package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.service.PlaylistService;

import java.util.List;

public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(
            PlaylistService playlistService
    ) {
        this.playlistService = playlistService;
    }

    public List<Playlist> handleViewAllPlaylists() {

        return playlistService.getAllPlaylists();
    }

    public List<Playlist> handleGetPlaylistsByUserId(
            int userId
    ) {

        return playlistService.getPlaylistsByUserId(
                userId
        );
    }

    public Playlist handleGetPlaylistById(
            int id
    ) {

        return playlistService.getPlaylistById(
                id
        );
    }

    public List<Playlist> handleSearchPlaylist(
            String keyword
    ) {

        return playlistService.searchPlaylist(
                keyword
        );
    }

    public boolean handleCreatePlaylist(
            Playlist playlist
    ) {

        return playlistService.createPlaylist(
                playlist
        );
    }

    public boolean handleUpdatePlaylist(
            Playlist playlist
    ) {

        return playlistService.updatePlaylist(
                playlist
        );
    }

    public boolean handleDeletePlaylist(
            int id
    ) {

        return playlistService.deletePlaylist(
                id
        );
    }

    public boolean handleDeletePlaylist(
            int playlistId,
            int userId
    ) {

        return playlistService.deletePlaylist(
                playlistId,
                userId
        );
    }
}