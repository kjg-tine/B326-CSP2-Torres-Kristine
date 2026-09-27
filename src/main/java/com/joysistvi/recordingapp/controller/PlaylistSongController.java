package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.PlaylistSong;
import com.joysistvi.recordingapp.service.PlaylistSongService;

import java.util.List;

public class PlaylistSongController {

    private final PlaylistSongService playlistSongService;

    public PlaylistSongController(
            PlaylistSongService playlistSongService
    ) {
        this.playlistSongService = playlistSongService;
    }

    public List<PlaylistSong> handleViewAllPlaylistSongs() {

        return playlistSongService.getAllPlaylistSongs();
    }

    public List<PlaylistSong> handleGetSongsByPlaylistId(
            int playlistId
    ) {

        return playlistSongService.getSongsByPlaylistId(
                playlistId
        );
    }

    public boolean handleAddSongToPlaylist(
            int playlistId,
            int songId
    ) {

        return playlistSongService.addSongToPlaylist(
                playlistId,
                songId
        );
    }

    public boolean handleRemoveSongFromPlaylist(
            int playlistId,
            int songId
    ) {

        return playlistSongService.removeSongFromPlaylist(
                playlistId,
                songId
        );
    }

    // User-protected add
    public boolean handleAddSongToPlaylist(
            int playlistId,
            int songId,
            int userId
    ) {

        return playlistSongService.addSongToPlaylist(
                playlistId,
                songId,
                userId
        );
    }

    // User-protected remove
    public boolean handleRemoveSongFromPlaylist(
            int playlistId,
            int songId,
            int userId
    ) {

        return playlistSongService.removeSongFromPlaylist(
                playlistId,
                songId,
                userId
        );
    }
}